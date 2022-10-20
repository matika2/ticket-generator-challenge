# [Ticket Generator Challenge](https://github.com/lindar-open/ticket-generator-challenge)

I am applying for senior backend developer position to Lindar.

I offer this solution for the coding challenge as the first step of the application process.

## Task description
A small challenge that involves building a [Bingo 90](https://en.wikipedia.org/wiki/Bingo_(United_Kingdom)) ticket generator.

**Requirements:**

* Generate a strip of 6 tickets
    - Tickets are created as strips of 6, because this allows every number from 1 to 90 to appear across all 6 tickets. If they buy a full strip of six it means that players are guaranteed to mark off a number every time a number is called.
* A bingo ticket consists of 9 columns and 3 rows.
* Each ticket row contains five numbers and four blank spaces
* Each ticket column consists of one, two or three numbers and never three blanks.
    - The first column contains numbers from 1 to 9 (only nine),
    - The second column numbers from 10 to 19 (ten), the third, 20 to 29 and so on up until
    - The last column, which contains numbers from 80 to 90 (eleven).
* Numbers in the ticket columns are ordered from top to bottom (ASC).
* There can be **no duplicate** numbers between 1 and 90 **in the strip** (since you generate 6 tickets with 15 numbers each)

**Please make sure you add unit tests to verify the above conditions and an output to view the strips generated (command line is ok).**

Try to also think about the performance aspects of your solution. How long does it take to generate 10k strips?
The recommended time is less than 1s (with a lightweight random implementation)

## Solution
### Technologies
I use Java 8 with Maven to solve the challenge. Lombok and Junit is used as Maven dependencies. I used SonarList analysis to eliminate possible coding issues and code smells.

### Idea
Strip structure generation rules are identified into the following categories
- (Ticket) row related rules
- Ticket column (aka. triple) related rules
- Strip column related rules

All these rules are taken into consideration when random ticket structures are generated. However, not all subsequent generations will succeed - the generation rules may collide. In such scenarios we just try to re-generate the strip.
Shortcut rules are applied so that the randomized generation of 10000 strip of six is optimized.

The generation takes less than 1s on my computer, including 
- Unique ticket structure generation 
- Random number generation
- Strip validation

(The generation takes way less than 1s on my computer without strip validation though.)

Class StripGeneratorTest is parameterized by
- A feature flag for strip generation validation
- A feature flag for strip generation printout
- A limit for setting the number of strips to be generated

The test class will print the time it takes to generate the strips. Feature flags and the number of strips are also printed.

### Further optimization option
Depending on number of CPU cores of the execution environment it is worth considering parallel generation of strips on separate threads and consolidate the strips into a collection.

### Not-accepted idea (kept in code base)
Let's take a valid strip of six with blank spaces and without numbers. New ticket generation involves the steps below. Any combination of these steps maintain validity of the ticket.
- Shuffle tickets within a strip of six
- Shuffle rows within a single ticket
- Shuffle columns except the first and last ones
