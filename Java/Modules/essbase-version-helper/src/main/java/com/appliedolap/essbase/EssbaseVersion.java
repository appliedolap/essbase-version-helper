package com.appliedolap.essbase;

import com.essbase.api.session.IEssbase;

public class EssbaseVersion {

	public enum EssbaseFeature {
		/**
		 * This is the FDMEE (Drillbridge) style drill-through that uses a web-browser
		 */
		URL_DRILLTHROUGH("URL-based drill-through", V11_1_1_3),

		// https://docs.oracle.com/cd/E66975_01/doc.1221/esb_javadoc/com/essbase/api/dataquery/IEssCubeView.html#setProcessMissing_boolean_
		SET_PROCESS_MISSING("set process missing", V11_1_1_3),
		
		/**
		 * Smart Lists, whee. Banner 11.x feature.
		 */
		SMART_LISTS("smart lists", V11_1_1),

		/**
		 * Set the user locale (<code>IEssbase.setUserLocale(...)</code>)
		 */
		SET_USER_LOCALE("set user locale", V11_1_2),
		
		MESSAGE_HANDLER("Essbase message handler", V11_1_2),
		
		OPEN_MAXL_SESSION("execute MaxL", V11_1_2),
		
		VARYING_ATTRIBUTES("varying attributes", V11_1_2),
		
		DATE_FORMAT_STRING("date format string", V11_1_2),
		
		/**
		 * Needs constant IEssOlapFileObject.TYPE_OUTLINE
		 */
		GET_OUTLINE_FILE("get outline file", V11_1_2),
		
		GET_LOG_MESSAGE("get log message from exception", V11_1_2),
		
		/**
		 * Run-time substitution variables
		 */
		CALC_RUN_TIME_SUB_VARS("run-time substitution variables", V11_1_2_3),

		/**
		 * Essbase Studio ("BPM") drill-through
		 */
		BPM_DRILLTHROUGH("Essbase Studio drill-through", V11_1_2_4),

		/**
		 * Dedicated class for setting outline edit options when using
		 * <code>IEssCube.openOutline(...)</code>
		 */
		OUTLINE_EDIT_OPTION("outline edit options", V11_1_2_4),

		/**
		 * Outline XML exports were available starting in 11.1.2 (with a cast to an EssCube), and
		 * made public API later on.
		 */
		OUTLINE_XML_EXPORT("outline XML exports", V11_1_2),

		/**
		 * Became public API (e.g. available in <code>IEssCube</code>, not just <code>EssCube</code>
		 */
		OUTLINE_XML_EXPORT_PUBLIC_API("outline XML export in public API", V12_2);

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

	/**
	 * This is the first official version in the 11.x series, i.e., this is essentially the same as
	 * what V11 would be. Notable introductions:
	 * 
	 * <ul>
	 * <li>Smart Lists (<code>IEssMember.getSmartList()</code></li>
	 * </ul>
	 * 
	 * <p>
	 * Sidenote: for technical reasons, the Dodeca Essbase servlet considers smart lists to have
	 * been introduced in 11.1.2.4 (something to do with the outline API).
	 */
	public static final Version V11_1_1 = Version.of("11.1.1");

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

	public static final Version V11_1_2_1 = Version.of("11.1.2.1");

	/**
	 * Equivalent to version 11.1.2.3. Notable introductions:
	 * 
	 * <ul>
	 * <li>Run-time substitution variables (EssCalcRunTimeSubVarDesc)
	 * </ul>
	 */
	public static final Version V11_1_2_3 = Version.of("11.1.2.3");

	/**
	 * Equivalent to version 11.1.2.4. Notable introductions:
	 * 
	 * <ul>
	 * <li><code>EssOutlineEditOption</code> class</li>
	 * <li>BPM (Essbase Studio) drill-through, notably with an 'enum' value:
	 * <code>IEssLinkedObject.EEssLinkedObjectType.BPM_DRILL_THROUGH</code></li> (and related _INT_VALUE)
	 * </ul>
	 */
	public static final Version V11_1_2_4 = Version.of("11.1.2.4");

	public static final Version V12_2 = Version.of("12.2");

	public static final Version V12_2_1_1 = Version.of("12.2.1.1");

	private EssbaseVersion() {
	}

	public static boolean supports(EssbaseFeature feature) {
		return feature.isAvailable();
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
		return CURRENT.isGreaterOrEqual(V11_1_1);
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
