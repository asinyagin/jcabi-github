/**
 * Copyright (c) 2013-2014, JCabi.com
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met: 1) Redistributions of source code must retain the above
 * copyright notice, this list of conditions and the following
 * disclaimer. 2) Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided
 * with the distribution. 3) Neither the name of the jcabi.com nor
 * the names of its contributors may be used to endorse or promote
 * products derived from this software without specific prior written
 * permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
 * THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jcabi.github.mock;

import com.jcabi.github.Content;
import com.jcabi.github.Contents;
import com.jcabi.github.Repo;
import javax.json.Json;
import javax.json.JsonObject;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link MkContent}.
 * @author Andres Candal (andres.candal@rollasolution.com)
 * @version $Id$
 * @since 0.8
 */
public final class MkContentTest {

    /**
     * MkContent should be able to fetch its own repo.
     *
     * @throws Exception if some problem inside
     */
    @Test
    public void canGetOwnRepo() throws Exception {
        final Repo repo = MkContentTest.repo();
        final Contents contents = repo.contents();
        final Content content = contents.create(
            jsonContent("repo.txt", "for repo", "json repo")
        );
        MatcherAssert.assertThat(
            content.repo(),
            Matchers.is(repo)
        );
    }

    /**
     * MkContent should be able to fetch its own path.
     *
     * @throws Exception if some problem inside
     */
    @Test
    public void canGetOwnPath() throws Exception {
        final Contents contents = MkContentTest.repo().contents();
        final String path = "dummy.txt";
        final Content content = contents.create(
            jsonContent(path, "for path", "path test")
        );
        MatcherAssert.assertThat(
            content.path(),
            Matchers.is(path)
        );
    }

    /**
     * MkContent should be able to fetch its JSON representation.
     *
     * @throws Exception if some problem inside
     */
    @Test
    public void fetchesJsonRepresentation() throws Exception {
        final Contents contents = MkContentTest.repo().contents();
        final String path = "fake.txt";
        final Content content = contents.create(
            jsonContent(path, "for json", "json test")
        );
        MatcherAssert.assertThat(
            // @checkstyle MultipleStringLiterals (1 line)
            content.json().getString("name"),
            Matchers.is(path)
        );
    }

    /**
     * Get a JSON object for content creation.
     * @param path The path of the file
     * @param message Commit message
     * @param content File content
     * @return JSON representation of content attributes
     */
    private static JsonObject jsonContent(
        final String path,
        final String message,
        final String content
    ) {
        return Json.createObjectBuilder()
            .add("path", path)
            .add("message", message)
            .add("content", content)
            .build();
    }

    /**
     * Create a repo to work with.
     * @return Repo
     * @throws Exception If some problem inside
     */
    private static Repo repo() throws Exception {
        return new MkGithub().repos().create(
            Json.createObjectBuilder().add("name", "test").build()
        );
    }
}
