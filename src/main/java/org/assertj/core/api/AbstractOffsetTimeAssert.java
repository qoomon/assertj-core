/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package org.assertj.core.api;

import static org.assertj.core.error.ShouldBeAfter.shouldBeAfter;
import static org.assertj.core.error.ShouldBeAfterOrEqualsTo.shouldBeAfterOrEqualsTo;
import static org.assertj.core.error.ShouldBeBefore.shouldBeBefore;
import static org.assertj.core.error.ShouldBeBeforeOrEqualsTo.shouldBeBeforeOrEqualsTo;
import static org.assertj.core.error.ShouldBeEqualIgnoringNanos.shouldBeEqualIgnoringNanos;
import static org.assertj.core.error.ShouldBeEqualIgnoringSeconds.shouldBeEqualIgnoringSeconds;
import static org.assertj.core.error.ShouldBeEqualIgnoringTimezone.shouldBeEqualIgnoringTimezone;
import static org.assertj.core.error.ShouldHaveSameHourAs.shouldHaveSameHourAs;

import java.time.OffsetTime;

import org.assertj.core.internal.Failures;
import org.assertj.core.internal.Objects;

/**
 * Assertions for {@link java.time.OffsetTime} type from new Date &amp; Time API introduced in Java 8.
 *
 * @author Alexander Bischof
 */
public abstract class AbstractOffsetTimeAssert<S extends AbstractOffsetTimeAssert<S>>
    extends AbstractAssert<S, OffsetTime> {

  public static final String NULL_OFFSET_TIME_PARAMETER_MESSAGE = "The OffsetTime to compare actual with should not be null";

  /**
   * Creates a new <code>{@link org.assertj.core.api.AbstractOffsetTimeAssert}</code>.
   *
   * @param selfType the "self type"
   * @param actual the actual value to verify
   */
  protected AbstractOffsetTimeAssert(OffsetTime actual, Class<?> selfType) {
    super(actual, selfType);
  }

  // visible for test
  protected OffsetTime getActual() {
    return actual;
  }

  /**
   * Verifies that the actual {@code OffsetTime} is <b>strictly</b> before the given one.
   * <p>
   * Example :
   * <p>
   * 
   * <pre><code class='java'> assertThat(parse("12:00:00Z")).isBefore(parse("13:00:00Z"));</code></pre>
   *
   * @param other the given {@link java.time.OffsetTime}.
   * @return this assertion object.
   * @throws AssertionError if the actual {@code OffsetTime} is {@code null}.
   * @throws IllegalArgumentException if other {@code OffsetTime} is {@code null}.
   * @throws AssertionError if the actual {@code OffsetTime} is not strictly before the given one.
   */
  public S isBefore(OffsetTime other) {
    Objects.instance().assertNotNull(info, actual);
    assertOffsetTimeParameterIsNotNull(other);
    if (!actual.isBefore(other)) {
      throw Failures.instance().failure(info, shouldBeBefore(actual, other));
    }
    return myself;
  }

  /**
   * Same assertion as {@link #isBefore(java.time.OffsetTime)} but the {@link java.time.OffsetTime} is built from given
   * String, which
   * must follow <a href=
   * "http://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_OFFSET_TIME"
   * >ISO OffsetTime format</a> to allow calling {@link java.time.OffsetTime#parse(CharSequence)} method.
   * <p>
   * Example :
   * <p>
   * 
   * <pre><code class='java'> // you can express expected OffsetTime as String (AssertJ taking care of the conversion)
   * assertThat(parse("12:59Z")).isBefore("13:00Z");</code></pre>
   *
   * @param offsetTimeAsString String representing a {@link java.time.OffsetTime}.
   * @return this assertion object.
   * @throws AssertionError if the actual {@code OffsetTime} is {@code null}.
   * @throws IllegalArgumentException if given String is null or can't be converted to a {@link java.time.OffsetTime}.
   * @throws AssertionError if the actual {@code OffsetTime} is not strictly before the {@link java.time.OffsetTime}
   *           built
   *           from given String.
   */
  public S isBefore(String offsetTimeAsString) {
    assertOffsetTimeAsStringParameterIsNotNull(offsetTimeAsString);
    return isBefore(OffsetTime.parse(offsetTimeAsString));
  }

  /**
   * Verifies that the actual {@code OffsetTime} is before or equals to the given one.
   * <p>
   * Example :
   * <p>
   * 
   * <pre><code class='java'> assertThat(parse("12:00:00Z")).isBeforeOrEqualTo(parse("12:00:00Z"))
   *                               .isBeforeOrEqualTo(parse("12:00:01Z"));</code></pre>
   *
   * @param other the given {@link java.time.OffsetTime}.
   * @return this assertion object.
   * @throws AssertionError if the actual {@code OffsetTime} is {@code null}.
   * @throws IllegalArgumentException if other {@code OffsetTime} is {@code null}.
   * @throws AssertionError if the actual {@code OffsetTime} is not before or equals to the given one.
   */
  public S isBeforeOrEqualTo(OffsetTime other) {
    Objects.instance().assertNotNull(info, actual);
    assertOffsetTimeParameterIsNotNull(other);
    if (actual.isAfter(other)) {
      throw Failures.instance().failure(info, shouldBeBeforeOrEqualsTo(actual, other));
    }
    return myself;
  }

  /**
   * Same assertion as {@link #isBeforeOrEqualTo(java.time.OffsetTime)} but the {@link java.time.OffsetTime} is built
   * from given
   * String, which must follow <a href=
   * "http://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_OFFSET_TIME"
   * >ISO OffsetTime format</a> to allow calling {@link java.time.OffsetTime#parse(CharSequence)} method.
   * <p>
   * Example :
   * <p>
   * 
   * <pre><code class='java'> // you can express expected OffsetTime as String (AssertJ taking care of the conversion)
   * assertThat(parse("12:00:00Z")).isBeforeOrEqualTo("12:00:00Z")
   *                               .isBeforeOrEqualTo("13:00:00Z");</code></pre>
   *
   * @param offsetTimeAsString String representing a {@link java.time.OffsetTime}.
   * @return this assertion object.
   * @throws AssertionError if the actual {@code OffsetTime} is {@code null}.
   * @throws IllegalArgumentException if given String is null or can't be converted to a {@link java.time.OffsetTime}.
   * @throws AssertionError if the actual {@code OffsetTime} is not before or equals to the {@link java.time.OffsetTime}
   *           built from given String.
   */
  public S isBeforeOrEqualTo(String offsetTimeAsString) {
    assertOffsetTimeAsStringParameterIsNotNull(offsetTimeAsString);
    return isBeforeOrEqualTo(OffsetTime.parse(offsetTimeAsString));
  }

  /**
   * Verifies that the actual {@code OffsetTime} is after or equals to the given one.
   * <p>
   * Example :
   * <p>
   * 
   * <pre><code class='java'> assertThat(parse("13:00:00Z")).isAfterOrEqualTo(parse("13:00:00Z"))
   *                               .isAfterOrEqualTo(parse("12:00:00Z"));</code></pre>
   *
   * @param other the given {@link java.time.OffsetTime}.
   * @return this assertion object.
   * @throws AssertionError if the actual {@code OffsetTime} is {@code null}.
   * @throws IllegalArgumentException if other {@code OffsetTime} is {@code null}.
   * @throws AssertionError if the actual {@code OffsetTime} is not after or equals to the given one.
   */
  public S isAfterOrEqualTo(OffsetTime other) {
    Objects.instance().assertNotNull(info, actual);
    assertOffsetTimeParameterIsNotNull(other);
    if (actual.isBefore(other)) {
      throw Failures.instance().failure(info, shouldBeAfterOrEqualsTo(actual, other));
    }
    return myself;
  }

  /**
   * Same assertion as {@link #isAfterOrEqualTo(java.time.OffsetTime)} but the {@link java.time.OffsetTime} is built
   * from given
   * String, which must follow <a href=
   * "http://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_OFFSET_TIME"
   * >ISO OffsetTime format</a> to allow calling {@link java.time.OffsetTime#parse(CharSequence)} method.
   * <p>
   * Example :
   * <p>
   * 
   * <pre><code class='java'> // you can express expected OffsetTime as String (AssertJ taking care of the conversion)
   * assertThat(parse("13:00:00Z")).isAfterOrEqualTo("13:00:00Z")
   *                               .isAfterOrEqualTo("12:00:00Z");</code></pre>
   *
   * @param offsetTimeAsString String representing a {@link java.time.OffsetTime}.
   * @return this assertion object.
   * @throws AssertionError if the actual {@code OffsetTime} is {@code null}.
   * @throws IllegalArgumentException if given String is null or can't be converted to a {@link java.time.OffsetTime}.
   * @throws AssertionError if the actual {@code OffsetTime} is not after or equals to the {@link java.time.OffsetTime}
   *           built from
   *           given String.
   */
  public S isAfterOrEqualTo(String offsetTimeAsString) {
    assertOffsetTimeAsStringParameterIsNotNull(offsetTimeAsString);
    return isAfterOrEqualTo(OffsetTime.parse(offsetTimeAsString));
  }

  /**
   * Verifies that the actual {@code OffsetTime} is <b>strictly</b> after the given one.
   * <p>
   * Example :
   * <p>
   * 
   * <pre><code class='java'> assertThat(parse("13:00:00Z")).isAfter(parse("12:00:00Z"));</code></pre>
   *
   * @param other the given {@link java.time.OffsetTime}.
   * @return this assertion object.
   * @throws AssertionError if the actual {@code OffsetTime} is {@code null}.
   * @throws IllegalArgumentException if other {@code OffsetTime} is {@code null}.
   * @throws AssertionError if the actual {@code OffsetTime} is not strictly after the given one.
   */
  public S isAfter(OffsetTime other) {
    Objects.instance().assertNotNull(info, actual);
    assertOffsetTimeParameterIsNotNull(other);
    if (!actual.isAfter(other)) {
      throw Failures.instance().failure(info, shouldBeAfter(actual, other));
    }
    return myself;
  }

  /**
   * Same assertion as {@link #isAfter(java.time.OffsetTime)} but the {@link java.time.OffsetTime} is built from given a
   * String that
   * must follow <a href=
   * "http://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_OFFSET_TIME"
   * >ISO OffsetTime format</a> to allow calling {@link java.time.OffsetTime#parse(CharSequence)} method.
   * <p>
   * Example :
   * <p>
   * 
   * <pre><code class='java'> // you can express expected OffsetTime as String (AssertJ taking care of the conversion)
   * assertThat(parse("13:00:00Z")).isAfter("12:00:00Z");</code></pre>
   *
   * @param offsetTimeAsString String representing a {@link java.time.OffsetTime}.
   * @return this assertion object.
   * @throws AssertionError if the actual {@code OffsetTime} is {@code null}.
   * @throws IllegalArgumentException if given String is null or can't be converted to a {@link java.time.OffsetTime}.
   * @throws AssertionError if the actual {@code OffsetTime} is not strictly after the {@link java.time.OffsetTime}
   *           built from given String.
   */
  public S isAfter(String offsetTimeAsString) {
    assertOffsetTimeAsStringParameterIsNotNull(offsetTimeAsString);
    return isAfter(OffsetTime.parse(offsetTimeAsString));
  }

  /**
   * Same assertion as {@link #isEqualTo(Object)} (where Object is expected to be {@link java.time.OffsetTime}) but here
   * you
   * pass {@link java.time.OffsetTime} String representation that must follow <a href=
   * "http://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_OFFSET_TIME"
   * >ISO OffsetTime format</a> to allow calling {@link java.time.OffsetTime#parse(CharSequence)} method.
   * <p>
   * Example :
   * <p>
   * 
   * <pre><code class='java'> // you can express expected OffsetTime as String (AssertJ taking care of the conversion)
   * assertThat(parse("13:00:00Z")).isEqualTo("13:00:00Z");</code></pre>
   *
   * @param offsetTimeAsString String representing a {@link java.time.OffsetTime}.
   * @return this assertion object.
   * @throws AssertionError if the actual {@code OffsetTime} is {@code null}.
   * @throws IllegalArgumentException if given String is null or can't be converted to a {@link java.time.OffsetTime}.
   * @throws AssertionError if the actual {@code OffsetTime} is not equal to the {@link java.time.OffsetTime} built from
   *           given String.
   */
  public S isEqualTo(String offsetTimeAsString) {
    assertOffsetTimeAsStringParameterIsNotNull(offsetTimeAsString);
    return isEqualTo(OffsetTime.parse(offsetTimeAsString));
  }

  /**
   * Same assertion as {@link #isNotEqualTo(Object)} (where Object is expected to be {@link java.time.OffsetTime}) but
   * here you
   * pass {@link java.time.OffsetTime} String representation that must follow <a href=
   * "http://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_OFFSET_TIME"
   * >ISO OffsetTime format</a> to allow calling {@link java.time.OffsetTime#parse(CharSequence)} method.
   * <p>
   * Example :
   * <p>
   * 
   * <pre><code class='java'> // you can express expected OffsetTime as String (AssertJ taking care of the conversion)
   * assertThat(parse("13:00:00Z")).isNotEqualTo("12:00:00Z");</code></pre>
   *
   * @param offsetTimeAsString String representing a {@link java.time.OffsetTime}.
   * @return this assertion object.
   * @throws AssertionError if the actual {@code OffsetTime} is {@code null}.
   * @throws IllegalArgumentException if given String is null or can't be converted to a {@link java.time.OffsetTime}.
   * @throws AssertionError if the actual {@code OffsetTime} is equal to the {@link java.time.OffsetTime} built from
   *           given String.
   */
  public S isNotEqualTo(String offsetTimeAsString) {
    assertOffsetTimeAsStringParameterIsNotNull(offsetTimeAsString);
    return isNotEqualTo(OffsetTime.parse(offsetTimeAsString));
  }

  /**
   * Same assertion as {@link #isIn(Object...)} (where Objects are expected to be {@link java.time.OffsetTime}) but here
   * you
   * pass {@link java.time.OffsetTime} String representations that must follow <a href=
   * "http://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_OFFSET_TIME"
   * >ISO OffsetTime format</a> to allow calling {@link java.time.OffsetTime#parse(CharSequence)} method.
   * <p>
   * Example :
   * <p>
   * 
   * <pre><code class='java'> // you can express expected OffsetTimes as String (AssertJ taking care of the conversion)
   * assertThat(parse("13:00:00Z")).isIn("12:00:00Z", "13:00:00Z");</code></pre>
   *
   * @param offsetTimesAsString String array representing {@link java.time.OffsetTime}s.
   * @return this assertion object.
   * @throws AssertionError if the actual {@code OffsetTime} is {@code null}.
   * @throws IllegalArgumentException if given String is null or can't be converted to a {@link java.time.OffsetTime}.
   * @throws AssertionError if the actual {@code OffsetTime} is not in the {@link java.time.OffsetTime}s built from
   *           given Strings.
   */
  public S isIn(String... offsetTimesAsString) {
    checkIsNotNullAndNotEmpty(offsetTimesAsString);
    return isIn(convertToOffsetTimeArray(offsetTimesAsString));
  }

  /**
   * Same assertion as {@link #isNotIn(Object...)} (where Objects are expected to be {@link java.time.OffsetTime}) but
   * here you
   * pass {@link java.time.OffsetTime} String representations that must follow <a href=
   * "http://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#ISO_OFFSET_TIME"
   * >ISO OffsetTime format</a> to allow calling {@link java.time.OffsetTime#parse(CharSequence)} method.
   * <p>
   * Example :
   * <p>
   * 
   * <pre><code class='java'> // you can express expected OffsetTimes as String (AssertJ taking care of the conversion)
   * assertThat(parse("13:00:00Z")).isNotIn("12:00:00Z", "14:00:00Z");</code></pre>
   *
   * @param offsetTimesAsString Array of String representing a {@link java.time.OffsetTime}.
   * @return this assertion object.
   * @throws AssertionError if the actual {@code OffsetTime} is {@code null}.
   * @throws IllegalArgumentException if given String is null or can't be converted to a {@link java.time.OffsetTime}.
   * @throws AssertionError if the actual {@code OffsetTime} is in the {@link java.time.OffsetTime}s built from given
   *           Strings.
   */
  public S isNotIn(String... offsetTimesAsString) {
    checkIsNotNullAndNotEmpty(offsetTimesAsString);
    return isNotIn(convertToOffsetTimeArray(offsetTimesAsString));
  }

  private static Object[] convertToOffsetTimeArray(String... offsetTimesAsString) {
    OffsetTime[] dates = new OffsetTime[offsetTimesAsString.length];
    for (int i = 0; i < offsetTimesAsString.length; i++) {
      dates[i] = OffsetTime.parse(offsetTimesAsString[i]);
    }
    return dates;
  }

  private void checkIsNotNullAndNotEmpty(Object[] values) {
    if (values == null) throw new IllegalArgumentException("The given OffsetTime array should not be null");
    if (values.length == 0) throw new IllegalArgumentException("The given OffsetTime array should not be empty");
  }

  /**
   * Check that the {@link java.time.OffsetTime} string representation to compare actual {@link java.time.OffsetTime} to
   * is not null,
   * otherwise throws a {@link IllegalArgumentException} with an explicit message
   *
   * @param OffsetTimeAsString String representing the {@link java.time.OffsetTime} to compare actual with
   * @throws IllegalArgumentException with an explicit message if the given {@link String} is null
   */
  private static void assertOffsetTimeAsStringParameterIsNotNull(String OffsetTimeAsString) {
    // @format:off
	if (OffsetTimeAsString == null) throw new IllegalArgumentException("The String representing the OffsetTime to compare actual with should not be null");
	// @format:on
  }

  /**
   * Check that the {@link java.time.OffsetTime} to compare actual {@link java.time.OffsetTime} to is not null, in that
   * case throws a {@link IllegalArgumentException} with an explicit message
   *
   * @param other the {@link java.time.OffsetTime} to check
   * @throws IllegalArgumentException with an explicit message if the given {@link java.time.OffsetTime} is null
   */
  private static void assertOffsetTimeParameterIsNotNull(OffsetTime other) {
    if (other == null)
      throw new IllegalArgumentException("The OffsetTime to compare actual with should not be null");
  }

  /**
   * Verifies that actual and given {@code OffsetTime} have same hour, minute and second fields (nanosecond fields are
   * ignored in comparison).
   * <p>
   * Assertion can fail with OffsetTimes in same chronological nanosecond time window, e.g :
   * <p>
   * 23:00:<b>01.000000000</b>+01:00 and 23:00:<b>00.999999999</b>+01:00.
   * <p>
   * Assertion fails as second fields differ even if time difference is only 1ns.
   * <p>
   * Code example :
   * <p>
   * 
   * <pre><code class='java'> // successful assertions
   * OffsetTime OffsetTime1 = OffsetTime.of(12, 0, 1, 0, ZoneOffset.UTC);
   * OffsetTime OffsetTime2 = OffsetTime.of(12, 0, 1, 456, ZoneOffset.UTC);
   * assertThat(OffsetTime1).isEqualToIgnoringNanos(OffsetTime2);
   * 
   * // failing assertions (even if time difference is only 1ns)
   * OffsetTime OffsetTimeA = OffsetTime.of(12, 0, 1, 0, ZoneOffset.UTC);
   * OffsetTime OffsetTimeB = OffsetTime.of(12, 0, 0, 999999999, ZoneOffset.UTC);
   * assertThat(OffsetTimeA).isEqualToIgnoringNanos(OffsetTimeB);</code></pre>
   *
   * @param other the given {@link java.time.OffsetTime}.
   * @return this assertion object.
   * @throws AssertionError if the actual {@code OffsetTime} is {@code null}.
   * @throws IllegalArgumentException if other {@code OffsetTime} is {@code null}.
   * @throws AssertionError if the actual {@code OffsetTime} is not equal with nanoseconds ignored.
   */
  public S isEqualToIgnoringNanos(OffsetTime other) {
    Objects.instance().assertNotNull(info, actual);
    assertOffsetTimeParameterIsNotNull(other);
    if (!areEqualIgnoringNanos(actual, other)) {
      throw Failures.instance().failure(info, shouldBeEqualIgnoringNanos(actual, other));
    }
    return myself;
  }

  /**
   * Verifies that actual and given {@link java.time.OffsetTime} have same hour and minute fields (second and nanosecond
   * fields are
   * ignored in comparison).
   * <p>
   * Assertion can fail with OffsetTimes in same chronological second time window, e.g :
   * <p>
   * 23:<b>01:00</b>.000+01:00 and 23:<b>00:59</b>.000+01:00.
   * <p>
   * Assertion fails as minute fields differ even if time difference is only 1s.
   * <p>
   * Code example :
   * <p>
   * 
   * <pre><code class='java'> // successful assertions
   * OffsetTime OffsetTime1 = OffsetTime.of(23, 50, 0, 0, ZoneOffset.UTC);
   * OffsetTime OffsetTime2 = OffsetTime.of(23, 50, 10, 456, ZoneOffset.UTC);
   * assertThat(OffsetTime1).isEqualToIgnoringSeconds(OffsetTime2);
   * 
   * // failing assertions (even if time difference is only 1ms)
   * OffsetTime OffsetTimeA = OffsetTime.of(23, 50, 00, 000, ZoneOffset.UTC);
   * OffsetTime OffsetTimeB = OffsetTime.of(23, 49, 59, 999, ZoneOffset.UTC);
   * assertThat(OffsetTimeA).isEqualToIgnoringSeconds(OffsetTimeB);</code></pre>
   *
   * @param other the given {@link java.time.OffsetTime}.
   * @return this assertion object.
   * @throws AssertionError if the actual {@code OffsetTime} is {@code null}.
   * @throws IllegalArgumentException if other {@code OffsetTime} is {@code null}.
   * @throws AssertionError if the actual {@code OffsetTime} is not equal with second and nanosecond fields
   *           ignored.
   */
  public S isEqualToIgnoringSeconds(OffsetTime other) {
    Objects.instance().assertNotNull(info, actual);
    assertOffsetTimeParameterIsNotNull(other);
    if (!areEqualIgnoringSeconds(actual, other)) {
      throw Failures.instance().failure(info, shouldBeEqualIgnoringSeconds(actual, other));
    }
    return myself;
  }

  /**
   * Verifies that actual and given {@link java.time.OffsetTime} have same hour, minute, second and nanosecond fields).
   * <p>
   * Code examples :
   * <p>
   * 
   * <pre><code class='java'> // successful assertions
   * OffsetTime offsetTime = OffsetTime.of(12, 0, 0, 0, ZoneOffset.UTC);
   * OffsetTime offsetTime2 = OffsetTime.of(12, 0, 0, 0, ZoneOffset.MAX);
   * assertThat(offsetTime).isEqualToIgnoringTimezone(offsetTime2);
   * 
   * // failing assertions (even if time difference is only 1ms)
   * OffsetTime offsetTime = OffsetTime.of(12, 0, 0, 0, ZoneOffset.UTC);
   * OffsetTime offsetTime2 = OffsetTime.of(12, 1, 0, 0, ZoneOffset.UTC);
   * assertThat(offsetTime).isEqualToIgnoringTimezone(offsetTime2); </code></pre>
   *
   * @param other the given {@link java.time.OffsetTime}.
   * @return this assertion object.
   * @throws AssertionError if the actual {@code OffsetTime} is {@code null}.
   * @throws IllegalArgumentException if other {@code OffsetTime} is {@code null}.
   * @throws AssertionError if the actual {@code OffsetTime} is not equal with timezone ignored.
   */
  public S isEqualToIgnoringTimezone(OffsetTime other) {
    Objects.instance().assertNotNull(info, actual);
    assertOffsetTimeParameterIsNotNull(other);
    if (!areEqualIgnoringTimezone(actual, other)) {
      throw Failures.instance().failure(info, shouldBeEqualIgnoringTimezone(actual, other));
    }
    return myself;
  }

  /**
   * Verifies that actual and given {@code OffsetTime} have same hour fields (minute, second and nanosecond fields are
   * ignored in comparison).
   * <p>
   * Assertion can fail with OffsetTimes in same chronological second time window, e.g :
   * <p>
   * <b>01:00</b>:00.000+01:00 and <b>00:59:59</b>.000+01:00.
   * <p>
   * Time difference is only 1s but hour fields differ.
   * <p>
   * Code example :
   * <p>
   * 
   * <pre><code class='java'> // successful assertions
   * OffsetTime OffsetTime1 = OffsetTime.of(23, 50, 0, 0, ZoneOffset.UTC);
   * OffsetTime OffsetTime2 = OffsetTime.of(23, 00, 2, 7, ZoneOffset.UTC);
   * assertThat(OffsetTime1).hasSameHourAs(OffsetTime2);
   * 
   * // failing assertions (even if time difference is only 1ms)
   * OffsetTime OffsetTimeA = OffsetTime.of(01, 00, 00, 000, ZoneOffset.UTC);
   * OffsetTime OffsetTimeB = OffsetTime.of(00, 59, 59, 999, ZoneOffset.UTC);
   * assertThat(OffsetTimeA).hasSameHourAs(OffsetTimeB); </code></pre>
   *
   * @param other the given {@link java.time.OffsetTime}.
   * @return this assertion object.
   * @throws AssertionError if the actual {@code OffsetTime} is {@code null}.
   * @throws IllegalArgumentException if other {@code OffsetTime} is {@code null}.
   * @throws AssertionError if the actual {@code OffsetTime} is not equal ignoring minute, second and nanosecond
   *           fields.
   */
  public S hasSameHourAs(OffsetTime other) {
    Objects.instance().assertNotNull(info, actual);
    assertOffsetTimeParameterIsNotNull(other);
    if (!haveSameHourField(actual, other)) {
      throw Failures.instance().failure(info, shouldHaveSameHourAs(actual, other));
    }
    return myself;
  }

  /**
   * Returns true if both OffsetTime are in the same hour, minute and second, false
   * otherwise.
   *
   * @param actual the actual OffsetTime. expected not be null
   * @param other the other OffsetTime. expected not be null
   * @return true if both OffsetTime are in the same year, month and day of month, hour, minute and second, false
   *         otherwise.
   */
  private static boolean areEqualIgnoringNanos(OffsetTime actual, OffsetTime other) {
    return areEqualIgnoringSeconds(actual, other) && haveSameSecond(actual, other);
  }

  /**
   * Returns true if both OffsetTime are in the same hour and minute, false otherwise.
   *
   * @param actual the actual OffsetTime. expected not be null
   * @param other the other OffsetTime. expected not be null
   * @return true if both OffsetTime are in the same hour and minute, false otherwise.
   */
  private static boolean areEqualIgnoringSeconds(OffsetTime actual, OffsetTime other) {
    return haveSameHourField(actual, other) && haveSameMinute(actual, other);
  }

  /**
   * Returns true if both OffsetTime are in the same hour, minute, second and nanosecond false otherwise.
   *
   * @param actual the actual OffsetTime. expected not be null
   * @param other the other OffsetTime. expected not be null
   * @return true if both OffsetTime are in the same hour, minute, second and nanosecond false otherwise.
   */
  private static boolean areEqualIgnoringTimezone(OffsetTime actual, OffsetTime other) {
    return areEqualIgnoringNanos(actual, other) && haveSameNano(actual, other);
  }

  private static boolean haveSameNano(OffsetTime actual, OffsetTime other) {
    return actual.getNano() == other.getNano();
  }

  private static boolean haveSameSecond(OffsetTime actual, OffsetTime other) {
    return actual.getSecond() == other.getSecond();
  }

  private static boolean haveSameMinute(OffsetTime actual, OffsetTime other) {
    return actual.getMinute() == other.getMinute();
  }

  private static boolean haveSameHourField(OffsetTime actual, OffsetTime other) {
    return actual.getHour() == other.getHour();
  }

}