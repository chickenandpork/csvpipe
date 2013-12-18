depjardir = @datadir@/@PACKAGE@/jars
dist_depjar_DATA = $(JARS)


EXTRA_DIST = $(nodist_pkgdata_JAVA)
CONFIG_CLEAN_FILES = $(JARS)

AM_JAVACFLAGS= -cp $(top_builddir)/$(subdir)/$(JAVAROOT)

BUILDTMP=$(shell date +%s)

JARS := poi-3.8-20120326.jar poi-ooxml-3.8-20120326.jar xmlbeans-2.3.0.jar poi-ooxml-schemas-3.8-20120326.jar dom4j-1.6.1.jar commons-io-2.3.jar
BUILT_SOURCES := $(JARS)



poi-3.8-20120326.jar poi-examples-3.8-20120326.jar poi-excelant-3.8-20120326.jar poi-ooxml-3.8-20120326.jar poi-ooxml-schemas-3.8-20120326.jar poi-scratchpad-3.8-20120326.jar: poi-bin-3.8-20120326.tar.gz
	tar xOf $< '*/$@' > $@

commons-logging-1.1.jar junit-3.8.1.jar log4j-1.2.13.jar: poi-bin-3.8-20120326.tar.gz
	tar xOf $< '*/lib/$@' > $@

dom4j-1.6.1.jar stax-api-1.0.1.jar xmlbeans-2.3.0.jar: poi-bin-3.8-20120326.tar.gz
	tar xOf $< '*/ooxml-lib/$@' > $@

poi-bin-3.8-20120326.tar.gz:
	test -f $${TMPDIR-/tmp}/$(@F) && cp $${TMPDIR-/tmp}/$(@F) $@ || true
	test -f $@ || curl -Lo $@ http://archive.apache.org/dist/poi/release/bin/poi-bin-3.8-20120326.tar.gz || rm $@
	test \! -f $@ || tar tzf $@ >/dev/null || rm $@
	test -f $${TMPDIR-/tmp}/$(@F) || cp $@ $${TMPDIR-/tmp}/$(@F)
	touch $@



commons-io-2.3.jar: commons-io-2.3-bin.tar.gz
	tar xOf $< '*/$@' > $@

commons-io-2.3-bin.tar.gz:
	test \! -f $${TMPDIR-/tmp}/$(@F) || tar tzf $${TMPDIR-/tmp}/$(@F) >/dev/null || rm $${TMPDIR-/tmp}/$(@F)
	test -f $${TMPDIR-/tmp}/$(@F) && cp $${TMPDIR-/tmp}/$(@F) $@ || true
	test -f $@ || curl -Lo $@ http://www.gtlib.gatech.edu/pub/apache//commons/io/binaries/commons-io-2.3-bin.tar.gz
	test \! -f $@ || tar tzf $@ >/dev/null || rm $@
	test -f $@ || curl -Lo $@ http://archive.apache.org/dist/commons/io/binaries/commons-io-2.3-bin.tar.gz
	test -f $${TMPDIR-/tmp}/$(@F) || cp $@ $${TMPDIR-/tmp}/$(@F)
	touch $@


check-local: $(JARS)
	for i in $<; do jar tf $$i >/dev/null || exit 1; done

