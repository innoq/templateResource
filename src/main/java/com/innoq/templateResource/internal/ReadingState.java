/*
 * Copyright 2017 innoQ Deutschland GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.innoq.templateResource.internal;

import com.innoq.templateResource.Query;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This enumeration contains the various processing-states required
 * for reading an entire <em>TemplateResource</em>-file.
 */
public enum ReadingState {

    EXPECT_NAME {
        @Override
        public ReadingState process(String line, Context<Query> ctx, int lineNumber) {
            if (isEmpty(line)) {
                return EXPECT_NAME;
            } else  if (isName(line)) {
                final String name = extractNameFrom(line);
                ctx.current.setName(name);
                return EXPECT_COMMENT;
            } else {
                throw new IllegalStateException("in line " + lineNumber + ":  '" + line + "'  cannot be accepted! - String must start with '-- name:' ");
            }
        }

        private boolean isName(String line) {
            return line.matches("--.+ [name:]?.+");
        }

        private String extractNameFrom(String line) {
            if (line.matches("--.+name:.+")) {
                int position = line.lastIndexOf(':');
                return line.substring(position + 2).trim();
            } else {
                int minus = line.indexOf('-');
                while (line.startsWith("-")) {
                    line = line.substring(minus);
                    minus++;
                }
                return line.trim();
            }
        }
    },

    EXPECT_COMMENT {
        @Override
        public ReadingState process(String line, Context<Query> ctx, int lineNumber) {
            if (isEmpty(line)) {
                return EXPECT_COMMENT_SEPARATOR;
            } else if (isComment(line)) {
                final String comment = extractCommentFrom(line);
                if ( containsParamComment(line) ) {
                    return  EXPECT_PARAM_IN_COMMENT;
                } else {
                    ctx.current.appendToDescription(comment);
                    return EXPECT_COMMENT;
                }
            } else {
                throw new IllegalArgumentException("in line " + lineNumber + " '" + line + "' cannot be accepted!");
            }
        }

        private boolean isComment(String line) {
            return line.startsWith("--");
        }

        private String extractCommentFrom(String line) {
            int dash = line.indexOf('-');
            while (line.startsWith("-")) {
                line = line.substring(dash);
                dash++;
            }
            return line.trim();
        }

        private boolean containsParamComment(String line) {
            return line.matches("\\-+ Params:\\s?");
        }
    },

    EXPECT_COMMENT_SEPARATOR {
        @Override
        public ReadingState process(String line, Context<Query> ctx, int lineNumber) {
            if (isEmpty(line)) {
                return EXPECT_COMMENT_SEPARATOR;
            } else {
                return EXPECT_QUERY.process(line, ctx, lineNumber);
            }
        }
    },

    EXPECT_QUERY {
        @Override
        public ReadingState process(String line, Context<Query> ctx, int lineNumber) {
            if (isEmpty(line)) {
                return EXPECT_QUERY_SEPARATOR;
            } else {
                ctx.current.appendToStatement(line);
                if (containsParamQuery(line)) {
                    return EXPECT_PARAM_IN_QUERY.process(line, ctx, lineNumber);
                }
                return EXPECT_QUERY;
            }
        }

        private boolean containsParamQuery(String line) {
            return line.matches(".+:?.+");
        }
    },

    EXPECT_QUERY_SEPARATOR {
        @Override
        public ReadingState process(String line, Context<Query> ctx, int lineNumber) {
            if (isEmpty(line)) {
                return EXPECT_QUERY_SEPARATOR;
            } else {
                ctx.push(new Query());
                return EXPECT_NAME.process(line, ctx, lineNumber);
            }
        }
    },

    EXPECT_PARAM_IN_QUERY {
        @Override
        public ReadingState process(String line, Context<Query> ctx, int lineNumber) {
            return EXPECT_QUERY;
        }
    },

    EXPECT_PARAM_IN_COMMENT {
        @Override
        public ReadingState process(String line, Context<Query> ctx, int lineNumber) {

            if (isEmpty(line)) {
                return EXPECT_COMMENT_SEPARATOR;
            } else if (isComment(line)) {
                Matcher m = Pattern.compile(" (:((\\w|-)+))\\s?").matcher(line);

                while (m.find()) {
                    ctx.current.addParam(m.group().trim());
                }

                // TODO: verify that this behavior is acceptable:
                // ctx.current.appendToDescription(line);

                return EXPECT_PARAM_IN_COMMENT;
            } else {
                throw new IllegalArgumentException("in line " + lineNumber + " '" + line + "' cannot be accepted!");
            }
        }

        private boolean isComment(String line) {
            return line.startsWith("--");
        }
    };

    public abstract ReadingState process(String line, Context<Query> ctx, int lineNumber);

    public static final ReadingState INITIAL = EXPECT_NAME;

    private static boolean isEmpty(String line) {
        return  line.matches("");
    }
}
