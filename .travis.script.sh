#!/usr/bin/env bash

# TRAVIS_PULL_REQUEST == "false" for a normal branch commit, the PR number for a PR
# TRAVIS_BRANCH == target of normal commit or target of PR
# TRAVIS_SECURE_ENV_VARS == true if encrypted variables, e.g. SONAR_HOST is available
# TRAVIS_REPO_SLUG == the repository, e.g. vaadin/vaadin

mvn -Dmaven.repo.local=$TRAVIS_BUILD_DIR/localrepo -B -e -V -Dvaadin.testbench.developer.license=$TESTBENCH_LICENSE clean verify
