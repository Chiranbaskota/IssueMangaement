# Post/Issue Management System

A complete production-ready Spring Boot 3 backend application for managing posts and issues with role-based access control.


## Technology Stack

- **Spring Boot 3.2.0**
- **Spring Framework 6**
- **Spring Security 6** (Basic Authentication)
- **Spring Data JPA**
- **MySQL Database**
- **ModelMapper** for DTO mapping
- **Lombok** for reducing boilerplate
- **Jakarta Validation** for input validation
- **Swagger/OpenAPI 3** for API documentation
- **JWT Support** (Optional, included but not enabled by default)

## Features

### User Management
- User registration with automatic USER role assignment
- Default admin user with ROLE_ADMIN
- Basic Authentication using BCrypt password hashing
- Role-based access control (USER, ADMIN)

### Post Management
Users can:
- Create posts (saved as DRAFT)
- Submit posts for approval (DRAFT → PENDING_APPROVAL)
- Add comments to their own posts or approved posts
- View their own posts (all statuses) and all APPROVED posts

Admins can:
- View ALL posts regardless of status
- Approve posts (PENDING_APPROVAL → APPROVED)
- Reject posts (PENDING_APPROVAL → REJECTED)
- Close posts (APPROVED → CLOSED)
- Assign update notes to posts
- Comment on any post

### Post Workflow
```
DRAFT → PENDING_APPROVAL → APPROVED → CLOSED
              ↓
          REJECTED
```

### Post Types
- ISSUE
- COMPLAINT
- ANNOUNCEMENT
- LOST
- HELP

## Project Structure

```
com.project
├── config              # Security, CORS, Swagger, ModelMapper configs
├── controller          # REST API endpoints
├── dto                 # Data Transfer Objects
│   ├── auth           # Authentication DTOs
│   ├── post           # Post DTOs
│   └── comment        # Comment DTOs
├── entity              # JPA entities
├── enums               # Enumerations (PostType, PostStatus, RoleType)
├── exception           # Custom exceptions and global handler
├── repository          # Spring Data JPA repositories
├── service             # Business logic interfaces
│   └── impl           # Service implementations
├── util                # Utility classes
│   └── jwt            # JWT utilities (optional)
└── initializer         # Sample data loader
```

## Database Setup

1. Install MySQL and create a database:
```sql
CREATE DATABASE post_management_db;
```

2. Update `src/main/resources/application.properties` with your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/post_management_db
spring.datasource.username=root
spring.datasource.password=your_password
```

## Running the Application

1. Build the project:
```bash
mvn clean install
```

2. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Sample Users

The application initializes with the following users:

| Username | Password | Role | Email |
|----------|----------|------|-------|
| admin | admin123 | ADMIN | admin@example.com |
| john | john123 | USER | john@example.com |
| jane | jane123 | USER | jane@example.com |

## API Documentation

Swagger UI is available at: `http://localhost:8080/swagger-ui.html`

OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## API Endpoints

### Authentication
- `POST /auth/register` - Register a new user
- `POST /auth/login` - Login and get user details

### Posts
- `POST /api/posts` - Create a new post
- `PUT /api/posts/{id}/submit` - Submit post for approval
- `PUT /api/posts/{id}/approve` - Approve post (Admin only)
- `PUT /api/posts/{id}/reject` - Reject post (Admin only)
- `PUT /api/posts/{id}/close` - Close post (Admin only)
- `PUT /api/posts/{id}/assign-update` - Assign update notes (Admin only)
- `GET /api/posts/{id}` - Get post by ID
- `GET /api/posts` - Get all posts (Admin only)
- `GET /api/posts/approved` - Get all approved posts
- `GET /api/user/posts` - Get current user's posts

### Comments
- `POST /api/posts/{postId}/comments` - Add comment to a post
- `GET /api/posts/{postId}/comments` - Get all comments for a post

## Authentication

The application uses HTTP Basic Authentication. Include credentials in the Authorization header:

```
Authorization: Basic base64(username:password)
```

### Example using cURL:
```bash
curl -u admin:admin123 http://localhost:8080/api/posts
```

### Example Request Body for Creating a Post:
```json
{
  "title": "Internet connectivity issue",
  "description": "The WiFi in building A is not working",
  "type": "ISSUE"
}
```

## Role-Based Access Control

| Action | USER | ADMIN |
|--------|------|-------|
| Create post | ✓ | ✓ |
| Submit post for approval | ✓ | ✗ |
| Approve post | ✗ | ✓ |
| Reject post | ✗ | ✓ |
| Close post | ✗ | ✓ |
| Assign update | ✗ | ✓ |
| Add comment | ✓ | ✓ |
| View posts | Own + Approved | All |

## JWT Support (Optional)

JWT utilities are included in the `com.project.util.jwt` package but are not enabled by default. The application uses Basic Authentication as specified in the requirements.

To enable JWT authentication:
1. Add JWT filter to SecurityConfig
2. Configure JWT properties in application.properties
3. Update AuthController to return JWT tokens

## Error Handling

The application includes comprehensive error handling with proper HTTP status codes:
- 400 Bad Request - Validation errors, invalid operations
- 401 Unauthorized - Authentication failures
- 403 Forbidden - Authorization failures
- 404 Not Found - Resource not found
- 500 Internal Server Error - Unexpected errors

## Validation

All DTOs include proper validation annotations:
- Username: 3-50 characters
- Password: Minimum 6 characters
- Email: Valid email format
- Post title: Required
- Post type: Required
- Comment text: Required

## Sample Data

The application automatically loads sample data on startup:
- 2 roles (USER, ADMIN)
- 3 users (admin, john, jane)
- 6 posts in various states (DRAFT, PENDING_APPROVAL, APPROVED, REJECTED, CLOSED)

## License

This project is for educational and demonstration purposes.
