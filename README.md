# Courier Tracking

## Workflow
1. When application is started, stores initially added to StoreBuilder as value object.
2. Courier registers himself/herself and notifies server for initial location.
3. Courier notifies server when his/her current location changed.
4. When server recognizes courier's location changing, calculate courier's total travel distance and raise an application event.
5. CourierLocationChangedEventHandler checks courier location close to any store and logs it.
6. Courier's total travel distance could be fetched from endpoint as "GET courier/{id}".

## Technical Debt
1. DDD based clean architecture with command pattern is used.
2. H2 DB used as in memory for easy implementation. Tradeoff: in-memory db contents removed after application stops.
3. Open Api dependency added for easy call.
4. Happy path integration tests are added for maximum coverage, also some unit tests added.
5. Postman collection is added to directory named as "tools/postman".
