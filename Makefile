
#
# Makefile for TbTextEdit
#

DIR=tbtextedit
FLAGS=-Xlint -classpath "."


all: $(DIR)/TbTextEdit.class  $(DIR)/TbDocListener.class $(DIR)/TbMouseListener.class $(DIR)/TbChangeListener.class 

$(DIR)/TbChangeListener.class: $(DIR)/TbChangeListener.java
	javac $(FLAGS) $(DIR)/TbChangeListener.java

$(DIR)/TbTextEdit.class: $(DIR)/TbTextEdit.java
	javac $(FLAGS) $(DIR)/TbTextEdit.java

$(DIR)/TbDocListener.class: $(DIR)/TbDocListener.java
	javac $(FLAGS) $(DIR)/TbDocListener.java

$(DIR)/TbMouseListener.class: $(DIR)/TbMouseListener.java
	javac $(FLAGS) $(DIR)/TbMouseListener.java


run:
	java $(DIR).TbTextEdit

clean:
	rm $(DIR)/*.class
