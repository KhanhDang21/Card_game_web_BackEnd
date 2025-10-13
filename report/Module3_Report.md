## Báo cáo Module 3 – Ván đấu 2 người (Server Backend)

Họ tên: … (điền)
MSSV: … (điền)
Nhóm lớp: … (N5/N6/N9/N10/N11/N12 – điền)
Số nhóm BTL: … (điền)
Tên đề tài: Hệ thống chơi lật bài tìm cặp trực tuyến

### I. Phần mở đầu và chương thiết kế
- Mục tiêu Module 3: Xây dựng chức năng ván đấu hai người (tạo ván, theo dõi điểm, thuật toán chơi cơ bản, thoát, chơi lại, kết thúc) và cung cấp API cho giao diện ván đấu/kết thúc.
- Yêu cầu nghiệp vụ (tóm từ note.txt): đăng nhập; thách đấu khi đối thủ rảnh; lật đúng cặp +20 điểm; hết cặp hoặc hết 5 phút thì so điểm; thoát giữa chừng xử thua; thắng được cộng điểm chênh lệch (thoát giữa chừng cộng mặc định 200); có thể chơi tiếp; lưu kết quả server.
- Môi trường & công nghệ: Spring Boot 3.5.x, Java 21, Spring Security (JWT), Spring Data JPA, MySQL.

### II. Phân tích thiết kế tổng quan ứng dụng
- Kiến trúc: Controller → Service → Repository → Entity; trả về `ApiResponse<T>`.
- Bảo mật: Resource Server JWT; một số endpoint seed/tạo ván công khai để dễ thử nghiệm.
- Sơ đồ khối chức năng: Client (Đăng ký, Đăng nhập, Thách đấu, Chơi, Kết thúc/Chơi lại) ↔ Server (Quản lý user, mode/bài, ván, điểm, luật, tổng kết). 
- Use case tổng quan: Đăng ký/Đăng nhập; Thách đấu/Chấp nhận; Ván 2 người; Kết thúc/Chơi lại.
- Sơ đồ (đặt trong thư mục `report/diagrams`):
  - Use case: `usecase_module3.puml`
  - ERD/Class: `erd_class_module3.puml`
  - Tuần tự: `sequence_create_play_end.puml`

### III. Phân tích thiết kế chi tiết chức năng cá nhân (Module 3)
1) Mô hình dữ liệu (ERD):
   - Bảng: `user`, `mode`, `card`, `card_set`, `game_board`, `user_game_board` (đúng ERD đã yêu cầu).
2) API đã triển khai:
   - Seed (public):
     - POST `/modes` { "name": "easy" }
     - POST `/cards` { "name": "apple", "dataImage": null }
     - POST `/card-sets?modeId=1&cardId=<cardId>`
   - Người dùng: POST `/users`; POST `/auth/log-in` → token.
   - Ván đấu:
     - POST `/game/create?modeId=1&userAId=<idA>&userBId=<idB>` → `{ gameBoardId }`
     - GET `/game/{gameBoardId}/scores` (Auth)
     - POST `/game/{gameBoardId}/score?userId=<id>&delta=20` (Auth)
     - POST `/game/{gameBoardId}/end` (Auth)
3) Thuật toán/luồng xử lý:
   - Tạo ván: chọn `card_set` theo `mode`, tạo `game_board`, khởi tạo 2 dòng `user_game_board` điểm = 0.
   - Chơi: khi đúng cặp, client gọi API cộng `delta=20` cho người chơi.
   - Kết thúc: đặt `end_time` và cộng dồn `user_game_board.score` vào `user.total_score` của từng người.
   - Thoát/Chơi lại: dùng API `end` để kết thúc sớm; chơi lại bằng cách tạo ván mới. (Có thể mở rộng quy tắc +200 khi đối phương thoát.)
4) Biểu đồ kèm theo: Use case, ERD/Class, Tuần tự (xem `report/diagrams`).

### IV. Kết quả (ứng dụng)
- Kiến trúc ứng dụng: server backend Spring Boot; client có thể là web React/Angular (ngoài phạm vi báo cáo này). Thông tin các thành phần: Controller/Service/Repository/Entity như trên.
- Cài đặt & triển khai: cấu hình DB trong `application.properties`; chạy `mvn spring-boot:run`. Seed dữ liệu theo `guide.md`, sau đó đăng ký → đăng nhập → tạo ván → cập nhật điểm → kết thúc.
- Kết quả cá nhân đạt được:
  - Hoàn thiện mô hình dữ liệu và API Module 3 theo ERD.
  - Cơ chế theo dõi điểm và cộng dồn `total_score` khi kết thúc ván.
  - Tài liệu thử nghiệm (`guide.md`) và sơ đồ (`report/diagrams`).
- Tài liệu tham khảo: Spring Boot/Security/Data JPA docs.


