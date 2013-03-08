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

import java.awt.Point; // for javadoc only

import java.io.Serializable;

/**
 * Indicates that implementations may extract a <em>sub-value</em>
 * {@link Object}&mdash;the <em>part</em>&mdash;out of a conceptually
 * larger {@link Object}&mdash;the <em>whole</em>.
 *
 * <p>For example, an {@link Extractor} implementation might be able
 * to conceptually extract a {@link Number} out of a particular area
 * of a larger {@link String}.  Or another hypothetical {@link
 * Extractor} implementation might be able to extract a {@code
 * java.awt.Point} out of a {@link CharSequence}, and so on.</p>
 *
 * <p>As is evident from the type signature, neither the {@code W} nor
 * {@code P} parameters need be instances of one another.</p>
 *
 * <p>{@link Extractor}s are not mutually exclusive.  That is, one
 * {@link Extractor} may be capable of extracting a {@link Number} out
 * of the first four characters of a {@link String}, and another
 * {@link Extractor} might be capable of extracting a {@link Point}
 * out of the next two characters of the same {@link String}, and
 * still another {@link Extractor} might be able to extract some sort
 * of custom {@link Object} out of all six of those characters.</p>
 *
 * @param <P> the type of the {@link Object} that this {@link
 * Extractor} is capable of extracting (<strong>P</strong> is for
 * <strong>p</strong>art)
 *
 * @param <W> the type of the {@link Object} from which this {@link
 * Extractor} is capable of extracting other {@link Object}s
 * (<strong>W</strong> is for <strong>w</strong>hole)
 *
 * @author <a href="http://about.me/lairdnelson"
 * target="_parent">Laird Nelson</a>
 */
public interface Extractor<W, P> extends Serializable {
  
  /**
   * Extracts a sub-value {@link Object}&mdash;the
   * <em>part</em>&mdash;out of a conceptually larger {@link
   * Object}&mdash;the <em>whole</em>.
   *
   * @param value the {@link Object} (the whole) from which a portion
   * (the part) will be extracted; may be {@code null}
   *
   * @return an {@link Object} representing the extracted sub-value of
   * the supplied {@code value} (the part); may be {@code null}
   *
   * @exception IllegalArgumentException if {@code value} is
   * unsuitable for performing extraction on for any reason
   */
  public P extractFrom(final W value);
  
}
