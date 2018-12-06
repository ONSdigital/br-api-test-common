# Business Register API Test Common
[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](./LICENSE)

A library containing test related utilities in order to facilitate a common approach to testing across the
Business Register APIs.


### Development Tasks

Run unit tests with coverage:

    sbt clean coverage test coverageReport

Generate static analysis report:

    sbt scapegoat

Publish to the local Ivy repository:

    sbt clean publishLocal
