#!/bin/bash
YEAR=$(date '+%Y')
DAY=$(date '+%d')
DAY_NO_ZEROS="${DAY//0/}"

echo $YEAR
echo $DAY

cp -r /Users/maria.soleim@schibsted.com/dev/not-search/advent-of-code-2023/src/main/kotlin/dayy /Users/maria.soleim@schibsted.com/dev/not-search/advent-of-code-2023/src/main/kotlin/day${DAY}

AOC_SESSION_COOKIE="GET_FROM_BROWSER"

PUZZLE_URL="https://adventofcode.com/${YEAR}/day/${DAY_NO_ZEROS}/input"

PUZZLE_FILE="/Users/maria.soleim@schibsted.com/dev/not-search/advent-of-code-2023/src/main/kotlin/day${DAY}/input.txt"
PUZZLE_FILE_TEST="/Users/maria.soleim@schibsted.com/dev/not-search/advent-of-code-2023/src/main/kotlin/day${DAY}/input-test.txt"

curl -q -s "${PUZZLE_URL}" -H "cookie: session=${AOC_SESSION_COOKIE}" -o "${PUZZLE_FILE}" 2>/dev/null
touch "${PUZZLE_FILE_TEST}"