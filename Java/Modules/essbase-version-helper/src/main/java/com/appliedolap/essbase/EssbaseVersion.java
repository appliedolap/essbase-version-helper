package com.appliedolap.essbase;

import com.essbase.api.session.IEssbase;

public class EssbaseVersion {

	public enum EssbaseFeature {
		/**
		 * This is the FDMEE (Drillbridge) style drill-through that uses a web-browser
		 */
		URL_DRILLTHROUGH("URL-based drill-through", V11_1_1_3),

		/**
		 * Outline XML exports were available starting in 11.1.2 (with a cast to an EssCube), and
		 * made public API later on.
		 */
		// TODO: we could offer a refined version of this if we want, based on using the interface
		// as opposed to a cast on EssCube
		OUTLINE_XML_EXPORT("outline XML exports", V11_1_2);

		private String description;

		private Version availableVersion;

		private EssbaseFeature(String description, Version availableVersion) {
			this.description = description;
			this.availableVersion = availableVersion;
		}

		public String getDescription() {
			return description;
		}

		public Version getAvailableVersion() {
			return availableVersion;
		}

		public boolean isAvailable() {
			return isAtLeast(availableVersion);
		}

	}

	public static final Version CURRENT = Version.of(IEssbase.JAPI_VERSION);

	public static final Version V9 = Version.of("9");

	public static final Version V11 = Version.of("11");

	/**
	 * Notable additions in this API level:
	 * 
	 * <pre>
	 * - IEssCube.getDrillThroughURL() -> IEssURLDrillThrough
	 * - IEssCube.listDrillThroughURLs() -> IEssURLDrillThrough[] 
	 * </pre>
	 */
	public static final Version V11_1_1_3 = Version.of("11.1.1.3");

	/**
	 * Equivalent to version 11.1.2.0. Notable introductions:
	 * 
	 */
	public static final Version V11_1_2 = Version.of("11.1.2");

	public static final Version V12 = Version.of("12");

	private EssbaseVersion() {
	}

	public static boolean supports(EssbaseFeature feature) {
		switch (feature) {
		case URL_DRILLTHROUGH:
			return isAtLeast(V11_1_1_3);
		case OUTLINE_XML_EXPORT:
			return isAtLeast(V11_1_2);
		default:
			return false;
		}
	}

	/**
	 * Performs an assertion that a given feature is available in the available Essbase Java API.
	 * Feature detection is performed based on what we have programmed in this library (as opposed
	 * to some sort of dynamic feature check).
	 * 
	 * <p>
	 * As with all other operations in this library, feature detection/availability is based on the
	 * version of the Essbase Java library you are using, as opposed to the version of the Essbase
	 * server you are connecting to.
	 * 
	 * @param feature the feature to test for
	 * @throws an UnsupportedOperationException with detailed message if the feature is not availble
	 */
	public static void assertHasFeature(EssbaseFeature feature) {
		if (!supports(feature)) {
			throw new UnsupportedOperationException("Version " + IEssbase.JAPI_VERSION + " of the Essbase Java API does not support " + feature.getDescription() + ", this feature is available in "
					+ feature.getAvailableVersion().toString());
		}
	}

	public static boolean isAtLeast(Version version) {
		return CURRENT.isGreaterOrEqual(version);
	}

	public static boolean isLessThan(Version version) {
		return !CURRENT.isGreaterOrEqual(version);
	}

	public static boolean essbaseIsAtLeast(Version version) {
		return CURRENT.isGreaterOrEqual(version);
	}

	public static boolean essbaseIsAtLeast11() {
		return CURRENT.isGreaterOrEqual(V11);
	}

	// :(
	public static <E> E fieldIfAtleast(Version version, E desired, E safe) {
		if (isAtLeast(version)) {
			return desired;
		} else {
			return safe;
		}
	}

}
