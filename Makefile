NAME = Fractal
JC = javac
JFLAGS = -g
MANIFEST = manifest.txt

JAR = $(NAME).jar
HTML = index.html

define applethtml
<applet code="$(NAME)" archive="$(JAR)" width="1024" height="768" ></applet>
endef
export applethtml

jar: $(JAR)

.INTERMEDIATE: *.class
classes:
	$(JC) $(JFLAGS) *.java

$(HTML):
	echo "$$applethtml" >> $@

run: $(HTML) $(JAR)
	appletviewer $<

clean:
	$(RM) *.class *.jar

$(JAR): classes
	jar cvfe $(JAR) $(NAME) *.class
	$(RM) *.class
