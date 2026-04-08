Real-Time Transaction Processing System

A backend system built using Spring Boot, Apache Kafka, JPA, and REST APIs to process financial transactions asynchronously and expose user balances.

Features
- Kafka-based event-driven transaction processing
- Transaction validation and balance checks
- H2 database integration with Spring Data JPA
- External Incentive API integration
- REST endpoint to query user balances

Tech Stack
- Java
- Spring Boot
- Apache Kafka
- Spring Data JPA
- H2 Database
- REST APIs
- Maven

API Endpoint
Get Balance
GET /balance?userId=1`

Key Learnings
- Event-driven architecture
- Microservices-style integration
- Data consistency in financial operations
- REST API design and external API consumption

System Design Diagram
flowchart LR
    A[Transaction Producer / Test Input] -->|Publishes Transaction| B[Kafka Topic: trader-updates]

    B --> C[Spring Boot Kafka Listener<br/>TransactionListener]

    C --> D{Validate Transaction}
    D -->|Invalid| X[Discard Transaction]
    D -->|Valid| E[Fetch Sender & Recipient<br/>from H2 Database]

    E --> F[Call Incentive API<br/>POST /incentive]
    F --> G[Receive Incentive Amount]

    G --> H[Update Balances]
    H --> H1[Sender Balance = Sender - Amount]
    H --> H2[Recipient Balance = Recipient + Amount + Incentive]

    H --> I[Persist Transaction Record<br/>using Spring Data JPA]
    I --> J[(H2 Database)]

    J --> K[REST Controller<br/>GET /balance?userId=...]
    K --> L[Return Balance Response]

