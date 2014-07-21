
# java make file stuff
JBIN = /opt/jdk1.8.0_20/bin
JC = $(JBIN)/javac
JCLASSPATH = -cp bin/:/opt/pi4j/lib/*
JFLAGS = -sourcepath src/ -d bin/
JA = sudo $(JBIN)/java
JAFLAGS = -classpath bin/:.:classes:/opt/pi4j/lib/'*' -Dcom.pi4j.wiringpi
JD = $(JBIN)/javadoc
JAR = $(JBIN)/jar
srcdir = src
bindir = bin
libdir = lib
datadir = data
docsdir = docs
dirs = $(srcdir) $(bindir) $(libdir) $(datadir) $(docsdir)
SHELL = /bin/sh
.SUFFIXES:
.SUFFIXES: .java .class

#src & obj locations, might need to expand the wildcard with deeper packages
VPATH = src/:$(wildcard $(bindir)/*/)

# java variables
source = $(wildcard $(srcdir)/*.java)
objects = $(shell find ./bin/ -iname *.class -printf "%P\n") # java objects
mainsrc = $(shell grep -l main src/*.java)
main = $(notdir $(basename $(mainsrc) ) )
mainexe =  $(basename $(shell find ./bin/ -iname $(main).class -printf "%P\n"))

#manditory first target
.PHONY : all
all : withthemakingalready
	@echo ":: Project build completed successfully ::"

.PHONY : withthemakingalready
withthemakingalready :
	@echo ":: Making already Target ::"
	$(JC) $(JCLASSPATH) $(JFLAGS) $(source)

#standard target
.PHONY : clean
clean :
	@echo ":: Clean Target ::"
	-rm -R $(bindir)
	-mkdir $(bindir)
	-rm -R $(datadir)
	-mkdir $(datadir)
	-rm *.sh
	-rm *.jar
	-rm -R *~

.PHONY : dirs
dirs : 
	@echo ":: Dirs Target ::"
	-mkdir $(bindir)
	-mkdir $(libdir)
	-mkdir $(srcdir)
	-mkdir $(datadir)
	-mkdir $(docsdir)

.PHONY : cleandirs
cleandirs : 
	-rmdir $(srcdir)
	-rmdir $(bindir)
	-rmdir $(libdir)
	-rmdir $(datadir)
	-rmdir $(docsdir)

.PHONY : status
status :
	@echo " ::::::''''''''''''''''':::::: "
	@echo " ::       Make Status       :: "
	@echo " ::::::.................::::::............................... "
	@echo " :: Pwd     : $(PWD)"
	@echo " :: Objects : $(notdir $(basename $(objects) ) )"
	@echo " :: Source  : $(notdir $(basename $(source) ) )"
	@echo " :: Main Src: $(mainsrc)"
	@echo " :: Main    : $(main)"
	@echo " :: Main Ex : $(mainexe)"
	@echo " ::::::...................................................... "
#add something about the packages found here too :p you lazy sod.

.PHONY : run
run :
	@echo ":: Run Target ::" 
	$(JA) $(JCLASSPATH) $(JAFLAGS) $(mainexe)

.PHONY : runScript
runScript :
	-rm run.sh
	echo "#!/bin/bash" > run.sh
	echo pushd $(PWD) >> run.sh
	echo "$(JA) $(JCLASSPATH) $(JAFLAGS) $(mainexe)" >> run.sh
	echo popd >> run.sh
	chmod a+x run.sh

.PHONY : runSchedule
runSchedule : $(datadir)/default.cron runScript
	@echo ":: Run Schedule ::"
	crontab $(datadir)/default.cron

.PHONY : defaultSchedules
defaultSchedules : 
	@echo ":: Making Schedules ::" 
	echo 0,2,4,6,8,10,12,14,16,18,20,22,24,26,28,30,32,34,36,38,40,42,44,46,48,50,52,54,56,58 \* \* \* \*   $(PWD)/run.sh > $(datadir)/default.cron
	echo 0,5,10,15,20,25,30,35,40,45,50,55 \* \* \* \*  $(PWD)/run.sh > $(datadir)/5-min.cron
	echo 0,15,30,45 \* \* \* \*   $(PWD)/run.sh > $(datadir)/quater-hour.cron


# Java rules. This allows individual classes to be built (eg make MyClass.class will look for MyClass.java and build it).
%.class : %.java
	@echo ":: Java Default ::"
	$(JC) $(JCLASSPATH) $(JFLAGS) $(source)


