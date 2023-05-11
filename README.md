# Essbase Version Helper

This library provides convenience and utility methods for working with different versions of the 
Essbase Java API. This library requires that you have the Essbase Java API available in your program, 
as it relies on at least being able to resolve `IEssbase.JAPI_VERSION`. 

## Using the Library

You can easily perform version checks, for example, to determine if the current Essbase library
version is at least version 11.1.1.3:

	if (EssbaseVersion.isAtLeast(EssbaseVersion.V11_1_1_3)) {
		// ... stuff that requires at least 11.1.1.3
	}
	
As an added convenience there are a number of known Essbase features and their versions that can be
checked using the appropriate enum methods:

	if (EssbaseFeature.OUTLINE_XML_EXPORT.isAvailable()) {
		// ... do export operation on IEssCube/EssCube object ...
	}
	
(Internally, this library knows that the outline XML export functionality was first available in 
version 11.1.2, which it checks for).

## Compilation / Packaging Considerations

The usage of this library assumes that you are compiling against a modern version of the Essbase
Java API but that you may "swap in" an older version of the library, for example, in our Java 
distribution or a WAR file. As such, care should be taken that you do not inadvertently rely on a
symbol _in evaluated code_ that might not exist in your Essbase library. For example, this library
lets you easily do conditional execution based on the Essbase library version. This protects you 
from calling a symbol that might not exist. However, if you are using an Essbase Java API symbol in
your `import` statements or in a Java `switch` construct, you may be relying on a symbol that 
isn't available. 

For example, in the outline XML export example, there is a class you use:

	EssOtlExportOptions options = new EssOtlExportOptions();
	
Since this class doesn't exist until 11.1.2, in order to make sure that your program that is 
packaging with, say, a 9.x Java library doesn't explicitly include this symbol in the execution
code path, you need to perform the import as such:

	import com.essbase.api.datasource.*;
	
Rather than:

	import com.essbase.api.datasource.EssOtlExportOptions;
	
Similar care should be taken with `switch` statements. You may need/want to "unroll" the switch
into `ifs` that are guarded with a version check from this library.

## License

Licensed under the Apache Software License version 2.0.