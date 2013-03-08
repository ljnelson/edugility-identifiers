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
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Metadata about an {@link Identifier}.
 *
 * <p>An {@link IdType} combines an optional {@linkplain #getName()
 * <em>name</em>} and a {@linkplain #getType() <em>type</em>}
 * together, along with the {@linkplain #extractFrom(Object,
 * Serializable) ability to mine the values of suitably-typed
 * <code>Identifier</code>s}.  These portions of reusable metadata are
 * bundled into the {@link IdType} class so that {@link IdType}
 * instances can be shared by {@link Identifier}s.</p>
 *
 * @param <W> the ({@link Serializable}) type of {@linkplain
 * Identifier#getValue() value} that {@link Identifier}s {@linkplain
 * Identifier#getIdType() with this <code>IdType</code>}
 * have&mdash;usually but not necessarily {@link String}, {@link
 * Integer} or {@link Long}
 *
 * @param <P> the common supertype that all embedded information in an
 * {@link Identifier} who has this {@link IdType} as {@linkplain
 * Identifier#getIdType() its type} possesses.  A {@link String} value
 * of an {@link Identifier}, for example, might be parseable into
 * separate sub-information sequences; this type parameter represents
 * the common type that these sub-information sequences share or
 * represent.  Often equal to {@code <W>}, but not always.
 *
 * @author <a href="http://about.me/lairdnelson"
 * target="_parent">Laird Nelson</a>
 */
public class IdType<W extends Serializable, P> implements Serializable {

  /**
   * The version of this class for {@linkplain Serializable
   * serialization purposes}.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The {@link Class} that defines the type of the {@linkplain
   * Identifier#getValue() value} of an {@link Identifier} with this
   * {@link IdType} {@linkplain Identifier#getIdType() as its
   * type/metadata}.
   *
   * <p>This field is never {@code null}.</p>
   *
   * @see #getType()
   */
  private final Class<W> type;
  
  /**
   * The name or label of this {@link IdType}.
   *
   * <p>This field may be {@code null}.</p>
   *
   * @see #getName()
   */
  private final String name;

  /**
   * A {@link Map} of {@link Extractor}s indexed by arbitrary keys.
   *
   * <p>This field is never {@code null}.</p>
   * 
   * @see #getExtractorKeys()
   *
   * @see #extractFrom(Object, Serializable)
   */
  private final Map<Object, Extractor<W, P>> extractors;


  /*
   * Constructors.
   */


  /**
   * Creates a new anonymous {@link IdType}.
   *
   * @param type the {@link Class} that defines the kind of thing any
   * {@link Identifier} fundamentally "is" when it has this {@link
   * IdType} as {@linkplain Identifier#getIdType() its type}; must not
   * be {@code null}
   *
   * @exception IllegalArgumentException if {@code type} is {@code
   * null}
   *
   * @see #IdType(Class, String, Map)
   */
  public IdType(final Class<W> type) {
    this(type, null, (Map<?, ? extends Extractor<W, P>>)null);
  }

  /**
   * Creates a new {@link IdType}.
   *
   * @param type the {@link Class} that defines the kind of thing any
   * {@link Identifier} fundamentally "is" when it has this {@link
   * IdType} as {@linkplain Identifier#getIdType() its type}; must not
   * be {@code null}
   *
   * @param name the name or label of this {@link IdType}; may be
   * {@code null}
   *
   * @exception IllegalArgumentException if {@code type} is {@code
   * null}
   *
   * @see #IdType(Class, String, Map)
   */
  public IdType(final Class<W> type, final String name) {
    this(type, name, (Map<?, ? extends Extractor<W, P>>)null);
  }

  /**
   * Creates a new anonymous {@link IdType}.
   *
   * @param type the {@link Class} that defines the kind of thing any
   * {@link Identifier} fundamentally "is" when it has this {@link
   * IdType} as {@linkplain Identifier#getIdType() its type}; must not
   * be {@code null}
   *
   * @param extractorKey the {@link Object} under which the supplied
   * {@link Extractor} will be indexed; may be {@code null}
   *
   * @param extractor an {@link Extractor} to index; may be {@code null}
   *
   * @exception IllegalArgumentException if {@code type} is {@code
   * null}
   *
   * @see #IdType(Class, String, Map)
   */
  public IdType(final Class<W> type, final Object extractorKey, final Extractor<W, P> extractor) {
    this(type, null, new HashMap<Object, Extractor<W, P>>() {
        private static final long serialVersionUID = 1L;
        { this.put(extractorKey, extractor); }
      });
  }

  /**
   * Creates a new {@link IdType}.
   *
   * @param type the {@link Class} that defines the kind of thing any
   * {@link Identifier} fundamentally "is" when it has this {@link
   * IdType} as {@linkplain Identifier#getIdType() its type}; must not
   * be {@code null}
   *
   * @param name the name or label of this {@link IdType}; may be
   * {@code null}
   *
   * @param extractorKey the {@link Object} under which the supplied
   * {@link Extractor} will be indexed; may be {@code null}
   *
   * @param extractor an {@link Extractor} to index; may be {@code null}
   *
   * @exception IllegalArgumentException if {@code type} is {@code
   * null}
   */
  public IdType(final Class<W> type, final String name, final Object extractorKey, final Extractor<W, P> extractor) {
    this(type, name, new HashMap<Object, Extractor<W, P>>() {
        private static final long serialVersionUID = 1L;
        { this.put(extractorKey, extractor); }
      });
  }

  /**
   * Creates a new anonymous {@link IdType}.
   *
   * @param type the {@link Class} that defines the kind of thing any
   * {@link Identifier} fundamentally "is" when it has this {@link
   * IdType} as {@linkplain Identifier#getIdType() its type}; must not
   * be {@code null}
   *
   * @param extractors a {@link Map} of {@link Extractor}s indexed by
   * arbitrary keys; may be {@code null}
   *
   * @exception IllegalArgumentException if {@code type} is {@code
   * null}
   */
  public IdType(final Class<W> type, final Map<?, ? extends Extractor<W, P>> extractors) {
    this(type, null, extractors);
  }

  /**
   * Creates a new {@link IdType}.
   *
   * @param type the {@link Class} that defines the kind of thing any
   * {@link Identifier} fundamentally "is" when it has this {@link
   * IdType} as {@linkplain Identifier#getIdType() its type}; must not
   * be {@code null}
   *
   * @param name the name or label of this {@link IdType}; may be
   * {@code null}
   *
   * @param extractors a {@link Map} of {@link Extractor}s indexed by
   * arbitrary keys; may be {@code null}
   *
   * @exception IllegalArgumentException if {@code type} is {@code
   * null}
   */
  public IdType(final Class<W> type, final String name, final Map<?, ? extends Extractor<W, P>> extractors) {
    super();
    if (type == null) {
      throw new IllegalArgumentException("type", new NullPointerException("type"));
    }
    this.type = type;
    this.name = name;
    if (extractors != null && !extractors.isEmpty()) {
      this.extractors = new HashMap<Object, Extractor<W, P>>(extractors);
    } else {
      this.extractors = Collections.emptyMap();
    }
  }


  /*
   * Methods constituting metadata about this IdType.
   */


  /**
   * Returns the {@link Class} that defines the kind of thing any
   * {@link Identifier} fundamentally "is" when it has this {@link
   * IdType} as {@linkplain Identifier#getIdType() its type}.
   *
   * <p>This method never returns {@code null}.</p>
   *
   * @return the {@link Class} that defines the kind of thing any
   * {@link Identifier} fundamentally "is" when it has this {@link
   * IdType} as {@linkplain Identifier#getIdType() its type}; never
   * {@code null}
   */
  public final Class<W> getType() {
    return this.type;
  }

  /**
   * Returns the name or label of this {@link IdType}.
   *
   * <p>This method may return {@code null}.</p>
   *
   * @return the name or label of this {@link IdType}, or {@code null}
   */
  public final String getName() {
    return this.name;
  }


  /*
   * Methods concerning subvalue extraction.
   */


  /**
   * Returns an {@link Extractor} installed under the supplied {@code
   * extractorId}, or {@code null}.
   *
   * @param extractorId the key under which the desired {@link
   * Extractor} is indexed; may be {@code null}
   *
   * @return an {@link Extractor}, or {@code null}
   */
  public final Extractor<W, P> getExtractor(final Object extractorId) {
    return this.extractors.get(extractorId);
  }

  /**
   * Returns a {@link Set} containing all known keys that can be used
   * to {@linkplain #getExtractor(Object) look up
   * <code>Extractor</code>s}.
   *
   * @return a non-{@code null} {@link Set} of extractor keys
   */
  public Set<Object> getExtractorKeys() {
    final Set<Object> returnValue;
    final Set<Object> extractorIds = this.extractors.keySet();
    if (extractorIds == null || extractorIds.isEmpty()) {
      returnValue = Collections.emptySet();
    } else {
      returnValue = Collections.unmodifiableSet(new HashSet<Object>(extractorIds));
    }
    return returnValue;
  }

  /**
   * Uses an {@link Extractor} {@linkplain #getExtractor(Object)
   * indexed under the supplied <code>extractorId</code>} to
   * {@linkplain Extractor#extractFrom(Object) extract} a sub-value
   * from the larger supplied {@code sourceValue} (which is usually
   * the return value from {@link Identifier#getValue()}).
   *
   * @param extractorId the identifier of the {@link Extractor} to
   * use; may be {@code null}
   *
   * @param sourceValue the value from which to extract; may be {@code
   * null}
   *
   * @return the extraction, or {@code null}
   */
  public P extractFrom(final Object extractorId, final W sourceValue) {
    P returnValue = null;
    final Extractor<W, P> extractor = this.getExtractor(extractorId);
    if (extractor != null) {
      returnValue = extractor.extractFrom(sourceValue);
    }
    return returnValue;
  }


  /*
   * Hashcode and equality methods.
   */


  /**
   * Returns a hashcode for this {@link IdType}.
   *
   * @return a hashcode for this {@link IdType}
   */
  @Override
  public int hashCode() {
    // http://www.linuxtopia.org/online_books/programming_books/thinking_in_java/TIJ313_029.htm
    int hashCode = 17;
    int c;

    // Include name
    final Object name = this.getName();
    c = name == null ? 0 : name.hashCode();
    hashCode = 37 * hashCode + c;

    // Include data type
    final Object type = this.getType();
    c = type == null ? 0 : type.hashCode();
    hashCode = 37 * hashCode + c;

    // Include extractors
    c = this.extractors.hashCode();
    hashCode = 37 * hashCode + c;

    return hashCode;
  }

  /**
   * Returns {@code true} if the supplied {@link Object} is equal to
   * this {@link IdType}.
   *
   * @param other the {@link Object} to test; may be {@code null}
   *
   * @return {@code true} if the supplied {@link Object} is equal to
   * this {@link IdType}; {@code false} otherwise
   */
  @Override
  public boolean equals(final Object other) {
    if (other == this) {
      return true;
    } else if (other != null && this.getClass().equals(other.getClass())) {
      final IdType him = (IdType)other;

      // Check names
      final Object name = this.getName();
      if (name == null) {
        if (him.getName() != null) {
          return false;
        }
      } else if (!name.equals(him.getName())) {
        return false;
      }

      // Check data types
      final Object type = this.getType();
      if (type == null) {
        if (him.getType() != null) {
          return false;
        }
      } else if (!type.equals(him.getType())) {
        return false;
      }

      // Check extractors      
      if (!this.extractors.equals(him.extractors)) {
        return false;
      }
      
      // All tests passed
      return true;
    } else {
      return false;
    }
  }


  /*
   * Formatting and representation methods.
   */


  /**
   * Returns a non-{@code null} {@link String} representation of this
   * {@link IdType}.
   *
   * @return a non-{@code null} {@link String} representation of this
   * {@link IdType}
   */
  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();

    final Object name = this.getName();
    if (name != null) {
      sb.append(name);
    }

    final Object type = this.getType();
    if (type != null) {
      if (name != null) {
        sb.append(" ");
      }
      sb.append("(");
      sb.append(type);
      sb.append(")");
    }

    return sb.toString();
  }

  /**
   * Returns a {@link URI} suitable for the supplied {@code value}, or
   * {@code null} if the supplied {@code value} could not be returned
   * either as a {@link URI} directly or wrapped by one.
   *
   * <p>In almost all cases this method will return {@linkplain
   * URI#isAbsolute() relative} {@link URI}s, since normally the
   * identity of an object is independent of where or how that object
   * is stored.</p>
   *
   * <p>This is not a requirement, however; URNs are valid {@link
   * URI}s that can be {@linkplain URI#isAbsolute() absolute} without
   * violating this principle.</p>
   *
   * <p>This method first checks the supplied {@code value} to see if
   * it is {@code null}.  If so, then it returns {@code null}.</p>
   *
   * <p>Next, this method checks to see if the supplied {@code value}
   * is an instance of {@link URI}.  If it is, then it is simply
   * returned.</p>
   *
   * <p>Finally, the return value from its {@linkplain
   * Object#toString() <code>toString()</code> method} is supplied to
   * the {@linkplain URI#URI(String) <code>URI</code> constructor} and
   * the resulting new {@link URI} is returned.</p>
   *
   * <p>Subclasses should feel free to override this method to do more
   * interesting things.</p>
   *
   * @param value the {@link Object} to convert; may be {@code null}
   * in which case {@code null} will be returned
   *
   * @return a new {@link URI}, the supplied {@code value} (if and
   * only if it is an instance of {@link URI}) or {@code null}
   *
   * @exception URISyntaxException if a new {@link URI} could not be
   * created
   */
  public URI toURI(final W value) throws URISyntaxException {
    if (value == null) {
      return null;
    } else if (value instanceof URI) {
      return (URI)value;
    } else {
      return new URI(value.toString());
    }
  }

  /**
   * Returns a non-{@code null} {@link IdType} whose {@linkplain
   * #getType() type} is equal&mdash;but not necessarily
   * identical&mdash;to the supplied {@code type}, and whose
   * {@linkplain #getName() name} is equal&mdash;but not necessarily
   * identical&mdash;to the supplied {@code name}, and whose internal
   * {@link Extractor} map is equal&mdash;but not necessarily
   * identical&mdash;to the supplied {@link Extractor} {@link Map}.
   *
   * @param <X> the ({@link Serializable}) type of {@linkplain
   * Identifier#getValue() value} that {@link Identifier}s {@linkplain
   * Identifier#getIdType() with the <code>IdType</code> returned by
   * this method} have&mdash;usually but not necessarily {@link
   * String}, {@link Integer} or {@link Long}
   *
   * @param <Y> the common supertype that all embedded information in
   * an {@link Identifier} who has the {@link IdType} returned by this
   * method as {@linkplain Identifier#getIdType() its type} possesses.
   * A {@link String} value of an {@link Identifier}, for example,
   * might be parseable into separate sub-information sequences; this
   * type parameter represents the common type that these
   * sub-information sequences share or represent.  Often equal to
   * {@code <W>}, but not always.
   *
   * @param type the {@link Class} that defines the kind of thing any
   * {@link Identifier} fundamentally "is" when it has this {@link
   * IdType} as {@linkplain Identifier#getIdType() its type}; must not
   * be {@code null}
   *
   * @param name the name or label of this {@link IdType}; may be
   * {@code null}
   *
   * @param extractors a {@link Map} of {@link Extractor}s indexed by
   * arbitrary keys; may be {@code null}
   *
   * @return a non-{@code null} {@link IdType}
   *
   * @exception IllegalArgumentException if {@code type} is {@code
   * null}
   */
  public static final <X extends Serializable, Y> IdType<X, Y> valueOf(final Class<X> type, final String name, final Map<Object, Extractor<X, Y>> extractors) {
    return new IdType<X, Y>(type, name, extractors);
  }


}
