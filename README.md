java-ai-techniques
==================

[![Build Status](https://github.com/s-webber/java-ai-techniques/actions/workflows/github-actions.yml/badge.svg)](https://github.com/s-webber/java-ai-techniques/actions/)

## About

This project contains basic examples of common artificial intelligence (AI) algorithms and data structures.

## Purpose

This project has been developed for the sole purpose of improving my understanding of AI - rather than as a comprehensive collection of efficient scalable techniques appropriate for use in mission critical systems.

## Approach

The techniques have been implemented using Java 7 and Google Guava. The unit tests have been implemented using JUnit and jMock.

## Building

The project can be built using Maven and Java 7 by executing the following from the command line: `mvn package`. Once you have executed this command, you will be able to view the JaCoCo test coverage report in the `target\site\jacoco` directory.

Javadoc can be produced by executing the following from the command line: `mvn javadoc:javadoc`. Once you have executed this command, you will be able to view the Javadoc in the `target\site\apidocs` directory.
