package org.assertj.core.api.recursive.comparison;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.configuration.ConfigurationProvider.CONFIGURATION_PROVIDER;
import static org.assertj.core.util.Strings.join;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.assertj.core.annotations.Beta;
import org.assertj.core.presentation.Representation;

@Beta
public class RecursiveComparisonConfiguration {

  // private boolean strictTypeCheck = true;

  private boolean ignoreAllActualNullFields = false;
  private Set<FieldLocation> ignoredFields = new LinkedHashSet<>();

  private List<Pattern> ignoredCustomEqualsRegexes = new ArrayList<>();

  // private Set<Class> forceRecursiveComparisonForTypes = new HashSet<>();
  // private Set<FieldLocation> forceRecursiveComparisonForFields = new HashSet<>();

  // private TypeComparators comparatorForTypes = new TypeComparators();
  // private FieldComparators comparatorForFields = new FieldComparators();

  private List<Pattern> ignoredFieldsRegexes = new ArrayList<>();

  public Comparator getComparatorForField(String fieldName) {
    return null;
  }

  public Comparator getComparatorForType(Class fieldType) {
    return null;
  }

  public boolean hasComparatorForField(String fieldName) {
    return false;
  }

  public boolean hasComparatorForType(Class<?> keyType) {
    return false;
  }

  public boolean hasNoCustomComparators() {
    return false;
  }

  public boolean shouldIgnoreAllActualNullFields() {
    return ignoreAllActualNullFields;
  }

  /**
   * Sets whether actual null fields are ignored in the recursive comparison.
   * <p>
   * TODO add a code example.
   *
   * @param ignoreAllActualNullFields
   */
  public void setIgnoreAllActualNullFields(boolean ignoreAllActualNullFields) {
    this.ignoreAllActualNullFields = ignoreAllActualNullFields;
  }

  /**
   * Register the given field paths as to be ignored in the comparison.
   * <p>
   * TODO add a code example.
   *
   * @param fieldPaths the field paths to be ignored in the comparison
   */
  public void ignoreFields(String... fieldPaths) {
    List<FieldLocation> fieldLocations = Stream.of(fieldPaths).map(FieldLocation::new).collect(toList());
    ignoredFields.addAll(fieldLocations);
  }

  public Set<FieldLocation> getIgnoredFields() {
    return ignoredFields;
  }

  boolean shouldIgnore(DualKey dualKey) {
    return matchesAnIgnoredNullField(dualKey)
           || matchesAnIgnoredField(dualKey)
           || matchesAnIgnoredRegex(dualKey);
  }

  public void ignoreFieldsByRegexes(String... regexes) {
    this.ignoredFieldsRegexes = Stream.of(regexes)
                                      .map(Pattern::compile)
                                      .collect(toList());
  }

  public void ignoreCustomEqualsByRegexes(String... regexes) {
    this.ignoredCustomEqualsRegexes = Stream.of(regexes)
                                            .map(Pattern::compile)
                                            .collect(toList());
  }

  public boolean shouldIgnoreOverriddenEquals(Class<? extends Object> clazz) {
    return matchesAnIgnoreOverriddenEqualsRegex(clazz);
  }

  @Override
  public String toString() {
    return multiLineDescription(CONFIGURATION_PROVIDER.representation());
  }

  public String multiLineDescription(Representation representation) { // TODO use representation ?
    StringBuilder description = new StringBuilder();
    if (ignoreAllActualNullFields) description.append(format("- all actual null fields were ignored in the comparison%n"));
    if (!ignoredFields.isEmpty())
      description.append(format("- the following fields were ignored in the comparison: %s%n", describeIgnoredFields()));
    if (!ignoredFieldsRegexes.isEmpty())
      description.append(format("- the following regexes were used to ignore fields in the comparison: %s%n",
                                describeRegexes(ignoredFieldsRegexes)));
    if (!ignoredCustomEqualsRegexes.isEmpty())
      description.append(format("- the following regexes were used to ignore overridden equals methods in the comparison: %s%n",
                                describeRegexes(ignoredCustomEqualsRegexes)));
    return description.toString();
  }

  // private stuff

  private boolean matchesAnIgnoreOverriddenEqualsRegex(Class<? extends Object> clazz) {
    if (this.ignoredCustomEqualsRegexes.isEmpty()) return false; // shortcut
    String canonicalName = clazz.getCanonicalName();
    return this.ignoredCustomEqualsRegexes.stream()
                                          .anyMatch(regex -> regex.matcher(canonicalName).matches());
  }

  private boolean matchesAnIgnoredNullField(DualKey dualKey) {
    return ignoreAllActualNullFields && dualKey.key1 == null;
  }

  private boolean matchesAnIgnoredRegex(DualKey dualKey) {
    return this.ignoredFieldsRegexes.stream()
                                    .anyMatch(regex -> regex.matcher(dualKey.concatenatedPath).matches());
  }

  private boolean matchesAnIgnoredField(DualKey dualKey) {
    return ignoredFields.stream()
                        .anyMatch(fieldLocation -> fieldLocation.matches(dualKey.concatenatedPath));
  }

  private String describeIgnoredFields() {
    List<String> fieldsDescription = ignoredFields.stream()
                                                  .map(FieldLocation::getFieldPath)
                                                  .collect(toList());
    return join(fieldsDescription).with(", ");
  }

  private String describeRegexes(List<Pattern> regexes) {
    List<String> fieldsDescription = regexes.stream()
                                            .map(Pattern::pattern)
                                            .collect(toList());
    return join(fieldsDescription).with(", ");
  }

}