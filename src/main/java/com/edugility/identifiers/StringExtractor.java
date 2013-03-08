/* -*- mode: Java; c-basic-offset: 2; indent-tabs-mode: nil -*-
 *
 * $Id$
 *
 * Copyright (c) 2012-2012 Edugility LLC.
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

public class StringExtractor implements Extractor<String, String> {
  
  private static final long serialVersionUID = 1L;
  
  private final int inclusiveStart;
  
  private final int exclusiveEnd;
  
  public StringExtractor(final int inclusiveStart, final int exclusiveEnd) {
    super();
    this.inclusiveStart = Math.max(0, inclusiveStart);
    this.exclusiveEnd = Math.max(inclusiveStart, exclusiveEnd);
  }
    
  @Override
  public String extractFrom(final String value) {
    if (value == null) {
      return null;
    }
    final int length = value.length();
    if (length - 1 < this.inclusiveStart) {
      return null;
    }
    return value.subSequence(this.inclusiveStart, Math.min(this.exclusiveEnd, length)).toString();
  }
    
  @Override
  public int hashCode() {
    return this.inclusiveStart + this.exclusiveEnd;
  }
    
  @Override
  public boolean equals(final Object other) {
    if (other == this) {
      return true;
    } else if (other != null && other.getClass().equals(this.getClass())) {
      final StringExtractor him = (StringExtractor)other;
      return this.inclusiveStart == him.inclusiveStart && this.exclusiveEnd == him.exclusiveEnd;
    } else {
      return false;
    }
  }
    
  @Override
  public String toString() {
    return String.format("%d,%d", this.inclusiveStart, this.exclusiveEnd);
  }
    
}  
