# Search Engine in Java Spring Boot

Welcome to this repository that presents a basic implementation of a search engine. It utilizes a web crawler to traverse given websites and follow the links found in each crawled page. The crawler algorithm is based on BFS, which ensures that pages closer to the starting point are crawled first. As the crawler traverses each page, it extracts its content, indexes it using Elasticsearch, and then proceeds to crawl other links discovered on the page.
## Technologies and tools

-   **Backend**  - Java, Spring Boot
    
-   **Database**  -
    
    -   **Redis**  - for storing the visited links and other information related to the bfs crawler (distance, etc)
    -   **ElasticSearch**  - for storing (**indexing**) the crawled pages content
-   Message broker:
    
    -   **Kafka**  - for bfs queue
    -   **AWS SQS**  - as alternative to kafka
-   Docker For running kafka and redis containers

## Requirements

-   Java 11
-   Maven 3.6.3 or higher
-   Doker

## Installation

1.  Clone the repository
```bash
git clone https://github.com/your-username/search-engine.git
```    
2.  Open the project in your preferred IDE.
    
3.  Configure the database settings in `application.properties`:
    
   
    
4.  Run the application:    
    The application will be available at `http://localhost:8080/swagger-ui.html`.
    
    

## License

This project is licensed under the MIT License. See the `LICENSE` file for details


