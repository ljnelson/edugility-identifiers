/* -*- mode: Java; c-basic-offset: 2; indent-tabs-mode: nil; coding: utf-8-unix -*-
 *
 * Copyright (c) 2011-2013 Edugility LLC.
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense and/or sell copies
 * of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT.  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 * The original copy of this license is available at
 * http://www.opensource.org/license/mit-license.html.
 */
package com.edugility.identifiers;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestCaseIdentifier {

  public TestCaseIdentifier() {
    super();
  }

  @Test
  public void testString() {
    final Extractor<String, String> extractor = new StringExtractor(0, 4);
    final Map<?, ? extends Extractor<String, String>> extractors = new HashMap<Object, Extractor<String, String>>() {
      private static final long serialVersionUID = 1L;
      { this.put("first segment", extractor); }
    };
    final IdType<String, String> glAccountIdType = new IdType<String, String>(String.class, "glAccountIdType", extractors);

    final Identifier<String, String> id = new Identifier<String, String>(glAccountIdType, "aaaabbbbccccdddd");
    assertEquals("aaaa", id.extractFrom("first segment"));
  }

  @Test
  public void testURIEdgeConditions() throws URISyntaxException {
    final URI uri = new URI("test%20");
    assertEquals("test ", uri.getSchemeSpecificPart());
  }

}
