package com.appliedolap.essbase;

import java.lang.reflect.Field;

public class EssbaseVersion {

	public enum EssbaseFeature {
		/**
		 * This is the FDMEE (Drillbridge) style drill-through that uses a web-browser
		 */
		URL_DRILLTHROUGH("URL-based drill-through", V11_1_1_3),

		// https://docs.oracle.com/cd/E66975_01/doc.1221/esb_javadoc/com/essbase/api/dataquery/IEssCubeView.html#setProcessMissing_boolean_
		SET_PROCESS_MISSING("set process missing", V11_1_1_3),

		/**
		 * Typed members refers to text measures and/or date measures. Banner 11.x feature.
		 */
		TYPED_MEMBERS("typed members - text and date measures", V11_1_1),

		/**
		 * Smart Lists a/k/a text measures, whee. Banner 11.x feature.
		 */
		TEXT_MEASURES("text measures / smart lists", V11_1_1),

		/**
		 * Smart Lists a/k/a text measures, whee. Banner 11.x feature.
		 */
		DATE_MEASURES("date measures", V11_1_1),

		/**
		 * Formatted values returned in the grid
		 */
		FORMATTED_VALUE("formatted values", V11_1_1),

		/**
		 * Member comment
		 */
		MEMBER_COMMENT("member comment", V11_1_1),

		/**
		 * MDX Operations in our server started with 11.1.1 even though Essbase may have supported it earlier)
		 */
		MDX_OPERATIONS("mdx operations", V11_1_1),

		/**
		 * Get or put file from a byte array.  Previous versions required writing the data to a file.
		 */
		GET_OR_PUT_FILE_FROM_BYTE_ARRAY("get or put file from a byte array", V11_1_1),

		/**
		 * Call is present to get the file size from the api - IEssOlapFileObject.getFileSizeLong()
		 */
		FILE_SIZE_FROM_API("get or put file from a byte array", V11_1_2_1),

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
		UNIQUE_NAME("unique name for duplicate members", V11_1_2_1),

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
		OUTLINE_XML_EXPORT_PUBLIC_API("outline XML export in public API", V12_2),

		/**
		 * Zoom in with ancestor members appearing at the top of their children instead of under their children.
		 */
		ANCESTOR_ON_TOP("zoom in with ancestor on top option", V19_3),

		/**
		 * Drillthrough via the Essbase server introduced in Essbase 19c.
		 */
		NATIVE_DRILLTHROUGH("drillthrough via Essbase server", V19_3);

		private final String description;

		private final Version availableVersion;

		EssbaseFeature(String description, Version availableVersion) {
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

	/**
	 * Traditionally this was <code>Version.of(IEssbase.JAPI_VERSION);</code> but if done so then the string will
	 * be inlined during compilation. We use reflection to pull it dynamically.
	 */
	public static final Version CURRENT;

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

	public static final Version V19_3 = Version.of("19.3");

	public static final Version V21_1 = Version.of("21.1");

	static {
		// none of these errors should be thrown, provided that the Essbase library is on the classpath
		try {
			Class<?> clazz = Class.forName("com.essbase.api.session.IEssbase");
			Field versionField = clazz.getField("JAPI_VERSION");
			String versionFieldValue = versionField.get(null).toString();
			CURRENT = Version.of(versionFieldValue);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException("Unable to access Essbase version from field", e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Unable to load Essbase class, probably means Essbase JAR is not available or on classpath", e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("Cannot access version field", e);
		}
	}

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
	 * @throws UnsupportedOperationException with detailed message if the feature is not availble
	 */
	public static void assertHasFeature(EssbaseFeature feature) {
		if (!supports(feature)) {
			throw new UnsupportedOperationException("Version " + CURRENT + " of the Essbase Java API does not support " + feature.getDescription() + ", this feature is available in "
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

}
