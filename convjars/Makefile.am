nodist_pkgdata_DATA = @PACKAGE@.jar

CONFIG_CLEAN_FILES = @PACKAGE@.jar

BUILDTMP=$(shell date +%s)

JAVAROOT = bld


@PACKAGE@.jar: ../java/@PACKAGE@.jar ../depjars/poi-3.8-20120326.jar ../depjars/poi-ooxml-3.8-20120326.jar ../depjars/xmlbeans-2.3.0.jar ../depjars/poi-ooxml-schemas-3.8-20120326.jar ../depjars/dom4j-1.6.1.jar ../depjars/commons-io-2.3.jar
	@-rm -fr $(@D)/.workdir
	@mkdir -p $(@D)/.workdir
	@echo "Manifest-Version: 1.0" > $(@D)/.workdir/MANIFEST.MF
	@echo "Created-By: @PACKAGE@-@VERSION@" >> $(@D)/.workdir/MANIFEST.MF
	echo "Main-Class: org.smallfoot.csv.CSVPipe"  >> $(@D)/.workdir/MANIFEST.MF
	ln -f $^ $(@D)/.workdir/
	for i in $(^F) ; do (cd $(@D)/.workdir  && jar xf $$i) ; done
	find $(@D)/.workdir '(' -name \*.java -o -name COPYING.LIB -o -name \*.html -o -name README -o -name Makefile -o -name ChangeLog -o -name GetoptDemo.class -o -name LANGUAGES -o -name \*.xml ')' -exec rm {} \;
	(cd $(@D)/.workdir && jar cm MANIFEST.MF gnu org) > $@
	rm -fr $(@D)/.workdir
	touch $@
