# Courier Tracking

## Workflow
1. Courier registers himself/herself and notifies server for initial location.
2. Courier notifies server when his/her current location changed.
3. When server recognizes courier's location changing, an application event raised.
4. That application event in 3; checks courier location close to any store and logs it.

## Technical Debt
1. DDD based clean architecture with command pattern is used.
2. H2 DB used as in memory for easy implementation. Tradeoff: in-memory db contents removed after application stops.
3. Open Api dependency added for easy call.
4. Happy path integration tests are added for maximum coverage, also some unit tests added.
5. Postman collection is added under tools/postman directory.
