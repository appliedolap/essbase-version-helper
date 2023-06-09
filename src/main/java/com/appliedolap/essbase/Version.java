package com.appliedolap.essbase;


import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Utility class for representing a version of something and providing comparison abilities, such as to differentiate
 * accurately between version 11.1.2.4 and version 12 of Essbase. Also used to compare at more granular levels, such as
 * comparing 11.1.2.4.010 to 11.1.2.4.048.
 */
public class Version implements Comparable<Version> {

	private int[] components;

	private String[] textComponents;

	public static final Comparator<Version> COMPARATOR = new Comparator<Version>() {
		@Override
		public int compare(Version o1, Version o2) {
			return o1.compareTo(o2);
		}
	};

	/**
	 * Create a new version given a text version string such as "11.1.2.4".
	 *
	 * @param version the version to parse
	 */
	public Version(String version) {
		// splits on a period or an underscore
		textComponents = version.split("[\\._]");
		components = new int[textComponents.length];

		for (int index = 0; index < textComponents.length; index++) {
			try {
				components[index] = Integer.valueOf(textComponents[index]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Could not parse version text " + version, e);
			}
		}
	}

	/**
	 * Convenience fluent method or building a new Version object.
	 *
	 * @param versionText the version to parse
	 * @return a new Version object
	 */
	public static Version of(String versionText) {
		return new Version(versionText);
	}

	/**
	 * Construct a new Version object with the explicit version components.
	 *
	 * @param components an array of components representing the version
	 */
	public Version(int... components) {
		this.components = components;
	}

	/**
	 * Returns the major version (first number). For example, 11.1.2.4 would be 11.
	 *
	 * @return the major version
	 */
	public int getMajor() {
		return getComponent(0);
	}

	/**
	 * Returns the minor version (second number). For example, 11.1.2.4 would be 1.
	 *
	 * @return the minor version
	 */
	public int getMinor() {
		return getComponent(1);
	}

	/**
	 * Returns the revision (third number). For example, 11.1.2.4 would be 2.
	 *
	 * @return the revision version
	 */
	public int getRevision() {
		return getComponent(2);
	}

	/**
	 * Returns the build (fourth number). For example, 11.1.2.4 would be 4.
	 *
	 * @return the build number
	 */
	public int getBuild() {
		return getComponent(3);
	}

	/**
	 * Gets a version component at the specified index. If it doesn't exist, 0 will be returned.
	 *
	 * @param index the index of the component in the original version
	 *
	 * @return the value, 0 if it isn't defined
	 */
	private int getComponent(int index) {
		if (index < components.length) {
			return components[index];
		} else {
			return 0;
		}
	}

	/**
	 * Checks if this Version object is higher than the Version represented by
	 * the passed in components. If the passed in components are more detailed
	 * (i.e., components.length is greater than the number of components in this
	 * version, the missing components on *this* version are treated like zero).
	 *
	 * @param components the components to check against
	 * @return true if this version is greater than the version represented by
	 *         the passed in components
	 */
	public boolean isGreaterOrEqual(int... components) {
		for (int index = 0; index < components.length; index++) {
			int thisComponent = getComponent(index);
			int thatComponent = components[index];

			if (thisComponent > thatComponent) {
				return true;
			} else if (thisComponent < thatComponent) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if the given version is greater or equal to this version.
	 *
	 * @param otherVersion the other version
	 * @return true if greater or equal, false otherwise
	 */
	public boolean isGreaterOrEqual(Version otherVersion) {
		return isGreaterOrEqual(otherVersion.getComponents());
	}

	/**
	 * Get all version components as an array of integers.
	 *
	 * @return all the version components as an array
	 */
	public int[] getComponents() {
		return components;
	}

	/**
	 * Gets the number of defined version components.
	 *
	 * @return the number of defined version components
	 */
	public int getNumComponents() {
		return components.length;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int index = 0; index < components.length; index++) {
			if (index > 0) {
				sb.append(".");
			}
			sb.append(components[index]);
		}
		return sb.toString();
	}

	/**
	 * Generates a text representation using the original parsed String values.
	 * This is an alternative method to use when printing when you need leading
	 * zeros on a version that would otherwise get lost in the conversion to a
	 * string.
	 *
	 * @return text representation of the version, based on original text values
	 */
	public String toTextString() {
		return Arrays.stream(textComponents).collect(Collectors.joining("."));
	}

	/**
	 * Compares one version to another using standard comparison philosophy.
	 * Checks component by component using the length of this Version of the
	 * comparison version, whichever is greater.
	 */
	@Override
	public int compareTo(Version o) {
		int numComponents = Math.max(this.getNumComponents(), o.getNumComponents());
		for (int index = 0; index < numComponents; index++) {
			if (getComponent(index) < o.getComponent(index)) {
				return -1;
			} else if (getComponent(index) > o.getComponent(index)) {
				return 1;
			}
		}
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(components);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Version other = (Version) obj;
		if (!Arrays.equals(components, other.components)) {
			return false;
		}
		return true;
	}

}