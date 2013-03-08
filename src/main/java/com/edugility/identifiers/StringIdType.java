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

import java.io.Serializable; // for javadoc only

/**
 * An {@link IdType} whose associated {@link Identifier}s {@linkplain
 * Identifier#getValue() have <code>String</code>s as their values}.
 *
 * @param <P> the common supertype that all embedded information in an
 * {@link Identifier} who has this {@link StringIdType} as {@linkplain
 * Identifier#getIdType() its type} possesses.  A {@link String} value
 * of an {@link Identifier}, for example, might be parseable into
 * separate sub-information sequences; this type parameter represents
 * the common type that these sub-information sequences share or
 * represent.  Often equal to {@code &lt;W&gt;}, but not always.
 *
 * @author <a href="http://about.me/lairdnelson"
 * target="_parent">Laird Nelson</a>
 *
 * @see IdType
 */
public class StringIdType<P> extends IdType<String, P> {
  
  /**
   * The version of this class for {@linkplain Serializable
   * serialization purposes}.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Creates a new {@link StringIdType}.
   *
   * <p>This constructor calls {@link IdType#IdType(Class, String)
   * super(String.class, name)}.</p>
   * 
   * @param name the name or label of this {@link IdType}; must not be
   * {@code null}
   *
   * @exception IllegalArgumentException if {@code name} is {@code
   * null}
   */
  public StringIdType(final String name) {
    super(String.class, name);
  }

}
