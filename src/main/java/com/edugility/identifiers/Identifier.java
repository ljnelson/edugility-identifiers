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

import java.io.Serializable;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.Collections;
import java.util.Set;

/**
 * An identifier.
 *
 * @param <W> the type of the {@link Identifier}'s {@linkplain
 * #getValue() value}; e.g. most commonly {@link String}, {@link
 * Integer} or {@link Long}
 *
 * @param <P> the common supertype of any semantic bits of information
 * that might be notionally embedded in the {@link Identifier}; e.g. a
 * long {@link String} identifier might have several meaningful
 * substrings in it so in this case {@code &lt;P&tg;} would be {@link
 * String String} as well.  On the other hand, the same {@link String}
 * might contain notional numbers, in which case perhaps {@link
 * Number} would be a better choice.
 *
 * @author <a href="http://about.me/lairdnelson"
 * target="_parent">Laird Nelson</a>
 */
public class Identifier<W extends Serializable, P> implements Serializable {

  /**
   * The version of this class for {@linkplain Serializable
   * serialization purposes}.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The {@link IdType} that says what kind of {@link Identifier} this
   * is.  This field is never {@code null}.
   *
   * @see #getIdType()
   */
  private final IdType<W, P> idType;

  /**
   * The value of this {@link Identifier}.  This field is never {@code
   * null}.
   *
   * @see #getValue()
   */
  private final W value;

  /**
   * Creates a new {@link Identifier}.
   *
   * @param idType the {@link IdType} that defines everything about
   * this {@link Identifier} except its actual {@linkplain #getValue()
   * value}; must not be {@code null}
   *
   * @param value the value this {@link Identifier} will have; must
   * not be {@code null}
   *
   * @exception IllegalArgumentException if either {@code idType} or
   * {@code value} is {@code null}
   */
  public Identifier(final IdType<W, P> idType, final W value) {
    super();
    if (idType == null) {
      throw new IllegalArgumentException("idType", new NullPointerException("idType"));
    }
    if (value == null) {
      throw new IllegalArgumentException("value", new NullPointerException("value"));
    }
    this.idType = idType;
    this.value = value;
  }

  /**
   * Returns the non-{@code null} {@link IdType} that defines
   * everything about this {@link Identifier} except its {@linkplain
   * #getValue() value}.
   *
   * @return a non-{@code null} {@link IdType}
   *
   * @see IdType
   */
  public final IdType<W, P> getIdType() {
    return this.idType;
  }

  /**
   * Returns the non-{@code null} value of this {@link Identifier}.
   */
  public final W getValue() {
    return this.value;
  }

  /**
   * Extracts a sub-value from this {@link Identifier}'s {@linkplain
   * #getValue() full value} {@linkplain IdType#extractFrom(Object,
   * Serializable) using its affiliated <code>IdType</code>} and
   * returns the result.
   *
   * <p>This method may return {@code null}.</p>
   *
   * @param extractorKey the {@linkplain #getExtractorKeys() key}
   * under which an {@linkplain IdType#getExtractor(Object)
   * <code>IdType</code>'s affiliated <code>Extractor</code>} might be
   * found; may be {@code null}
   *
   * @return the sub-value, or {@code null} if no such sub-value could
   * be extracted for any reason
   *
   * @see #getExtractorKeys()
   *
   * @see IdType#extractFrom(Object, Serializable)
   */
  public final P extractFrom(final Object extractorKey) {
    P returnValue = null;
    final IdType<W, P> idType = this.getIdType();
    if (idType != null) {
      returnValue = idType.extractFrom(extractorKey, this.getValue());
    }
    return returnValue;
  }

  /**
   * Returns all {@linkplain IdType#getExtractorKeys() known keys}
   * under which this {@link Identifier}'s {@linkplain #getIdType()
   * affiliated <code>IdType</code>} has indexed {@link Extractor}s.
   *
   * <p>This method never returns {@code null} and the {@link Set}
   * that is returned is {@linkplain Collections#unmodifiableSet(Set)
   * immutable} and a clone of the actual {@link Set} that is stored
   * by the {@linkplain #getIdType() affiliated
   * <code>IdType</code>}.</p>
   *
   * @return a non-{@code null} {@link Set} of extractor keys
   *
   * @see #extractFrom(Object)
   *
   * @see IdType#getExtractorKeys()
   */
  public final Set<Object> getExtractorKeys() {
    final Set<Object> keySet;
    final IdType<W, P> idType = this.getIdType();
    if (idType == null) {
      keySet = Collections.emptySet();
    } else {
      keySet = idType.getExtractorKeys();
    }
    return keySet;
  }

  /**
   * Returns a hashcode for this {@link Identifier} factoring in its
   * {@linkplain #getValue() value} and its {@link #getIdType()
   * IdType}.
   * 
   * @return a hashcode for this {@link Identifier}
   */
  @Override
  public int hashCode() {
    int hashCode = 17;
    int c;

    final Object value = this.getValue();
    c = value == null ? 0 : value.hashCode();
    hashCode = 37 * hashCode + c;

    final Object idType = this.getIdType();
    c = idType == null ? 0 : idType.hashCode();
    hashCode = 37 * hashCode + c;

    return hashCode;
  }

  /**
   * Returns {@code true} if the supplied {@link Object} is equal to
   * this {@link Identifier}.
   *
   * @param other the {@link Object} to test; may be {@code null} in
   * which case {@code false} will be returned
   *
   * @return {@code true} if the supplied {@link Object} has a {@link
   * Object#getClass() Class} that is equal to this {@link
   * Identifier}'s {@link Object#getClass() Class}, a {@linkplain
   * #getValue() value} that is equal to this {@link Identifier}'s
   * {@linkplain #getValue() value} and an {@link #getIdType() IdType}
   * that is equal to this {@link Identifier}'s {@link #getIdType()
   * IdType}; {@code false} otherwise
   */
  @Override
  public boolean equals(final Object other) {
    if (other == this) {
      return true;
    } else if (other != null && other.getClass().equals(this.getClass())) {
      final Identifier him = (Identifier)other;

      final Object value = this.getValue();
      if (value == null) {
        if (him.getValue() != null) {
          return false;
        }
      } else if (!value.equals(him.getValue())) {
        return false;
      }

      final Object idType = this.getIdType();
      if (idType == null) {
        if (him.getIdType() != null) {
          return false;
        }
      } else if (!idType.equals(him.getIdType())) {
        return false;
      }

      return true;
    } else {
      return false;
    }
  }

  /**
   * Returns a non-{@code null} {@link String} representation of this
   * {@link Identifier}.
   *
   * @return a non-{@code null} {@link String} representation of this
   * {@link Identifier}
   */
  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    
    final Object idType = this.getIdType();
    if (idType != null) {
      sb.append(idType);
    }

    final Object value = this.getValue();
    if (value != null) {
      if (idType != null) {
        sb.append(": ");
      }
      sb.append(value);
    }

    return sb.toString();
  }

  /**
   * Returns a possibly {@code null} {@link URI} representation of
   * this {@link Identifier}.
   *
   * @return a {@link URI} representation of this {@link Identifier},
   * or {@code null}
   *
   * @exception URISyntaxException if an error constructing the {@link
   * URI} occurred
   *
   * @see IdType#toURI(Serializable)
   */
  public URI toURI() throws URISyntaxException {
    URI returnValue = null;
    final IdType<W, P> idType = this.getIdType();
    if (idType != null) {
      returnValue = idType.toURI(this.getValue());
    }
    return returnValue;
  }

  
  /*
   * Static methods.
   */


  /**
   * Returns a non-{@code null} {@link Identifier} whose {@link
   * #getIdType() IdType} and {@linkplain #getValue() value} equal the
   * supplied {@link IdType} and {@code value}.
   *
   * <p>Currently, this method simply creates a new {@link Identifier}
   * with the supplied arguments, but the right is reserved for this
   * method to change to use a caching mechanism in much the same way
   * that {@link Integer#valueOf(int)} does.</p>
   *
   * @param <X> the type of the {@link Identifier}'s {@linkplain
   * #getValue() value}; e.g. most commonly {@link String}, {@link
   * Integer} or {@link Long}
   *
   * @param <Y> the common supertype of any semantic bits of information
   * that might be notionally embedded in the {@link Identifier}; e.g. a
   * long {@link String} identifier might have several meaningful
   * substrings in it so in this case {@code &lt;P&tg;} would be {@link
   * String String} as well.  On the other hand, the same {@link String}
   * might contain notional numbers, in which case perhaps {@link
   * Number} would be a better choice.
   *
   * @param idType the {@link IdType} that will be equal <strong>but
   * not necessarily identical</strong> to the {@linkplain
   * #getIdType() <code>IdType</code> associated with the
   * <code>Identifier</code> that this method will return}; must not
   * be {@code null}
   *
   * @param value the {@linkplain #getValue() value} that will be
   * equal <strong>but not necessarily identical</strong> to the
   * {@linkplain #getValue() value associated with the
   * <code>Identifier</code> that this method will return}; must not
   * be {@code null}
   *
   * @return a non-{@code null} {@link Identifier}
   *
   * @exception IllegalArgumentException if either parameter is {@code
   * null}
   */
  public static <X extends Serializable, Y> Identifier<X, Y> valueOf(final IdType<X, Y> idType, final X value) {
    return new Identifier<X, Y>(idType, value);
  }

}
