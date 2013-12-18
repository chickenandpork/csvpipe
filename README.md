csvpipe
=======

CSVPipe is a tool I use when forced to process CSV.  I tend to bring in accumulators and splitters to post-process where statistics and such at original can be heavy.


BUILDING

This is a standard AutoTools build, so:

1) ./autoreconf -vfi

2) make install (or "make check" to include that plus run the tests)

3) make rpm (if so inclined)


java/csvpipe.jar is the proper built jar file; convjars is where "convenience jars" are built if 
you're less concerned with license purity and more concerned with:

1) your tests are bogus.  Let me test exactly what you tested; or

2) I need it working like yesterday.  Holy crap please help me.
      Gimme something to download immediately to make the pain stop.


We've all been there.  In both cases.  grab convjars/csvpipe.jar, it's not sanitary, but it works.


USAGE

Example usage at:

    java -jar csvpipe.jar -H

