DIST_SUBDIRS =	depjars java convjars tests-autotest
SUBDIRS =	depjars
if DO_JAVA
SUBDIRS +=	java convjars
endif
SUBDIRS +=	tests-autotest

ACLOCAL_AMFLAGS = -I m4

# if missing mkinstalldirs install-sh trigger errors, ignore the result code on the "system (cp ...)" command in automake
# CONFIG_CLEAN_FILES = aclocal.m4 config.guess config.sub config.h config.h.in ltconfig ltmain.sh missing mkinstalldirs install-sh Doxyfile @PACKAGE@.spec @PACKAGE@.jar changes.atom
EXTRA_DIST = @PACKAGE@.spec gitdescribe



if DO_DOXYGEN
doc: Doxyfile
	$(DOXYGEN)
endif

if DO_ASTYLE
formatting: java/CSVPipe.java.in java/ColumnGroupingRowPrinter.java.in java/CsvRowPrinter.java.in java/DocSheetRowPrinter.java.in java/EpochExpanderRowPrinter.java.in java/GroupingRowPrinter.java.in java/LimitedIntervalsRowPrinter.java.in java/MergingRowPrinter.java java/NoCapHeader.java java/RowPrinter.java java/SummarizingRowPrinter.java.in java/version.java.in
if DO_GITFORMATTING
	@git ls-files --other --exclude-standard --directory || { echo "###################### unignored local files: git add, git commit ?"; }
	@git diff --exit-code || { echo "###################### unstaged changes: git commit -a"; exit 1; }
	@git diff --cached --exit-code || { echo "###################### staged uncommitted changes: git commit"; exit 1; }
endif
	$(MAKE) check
	for i in $^; do $(ASTYLE) -A1 $$i; done
	$(MAKE) check
if DO_GITFORMATTING
	git commit -a -m 'formatting and whitespace'
endif DO_GITFORMATTING

endif DO_ASTYLE

if DO_GITFORMATTING
gitdescribe: .git/HEAD
	git describe > $@
endif DO_GITFORMATTING


if HAVE_RPMBUILD
srpm: distcheck
	@mkdir -p $$(rpm --eval '%{_srcrpmdir}')
	@RPMBUILD@ -ts $(PACKAGE)-$(VERSION).tar.bz2
if DO_SUDOPOSTMOVE
	mv -f $$(rpm --eval '%{_srcrpmdir}')/@PACKAGE@-$$(rpm --qf '%{VERSION}\n' -q --specfile @PACKAGE@.spec |head -1)-$$(rpm --qf '%{RELEASE}\n' -q --specfile @PACKAGE@.spec |head -1).src.rpm @SUDOPOSTMOVE@
endif   DO_SUDOPOSTMOVE

if DO_GITFORMATTING
re-dist-bzip2: gitdescribe
	rm -f @PACKAGE@.spec $(PACKAGE)-$(VERSION).tar.bz2
	touch configure
	$(MAKE) dist-bzip2
rpm: re-dist-bzip2
else
rpm: dist-bzip2
endif DO_GITFORMATTING_FALSE
	@RPMBUILD@ --define 'skipcheck 1' -ta $(PACKAGE)-$(VERSION).tar.bz2
endif HAVE_RPMBUILD

