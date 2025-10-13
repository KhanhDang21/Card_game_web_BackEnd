## Xuất báo cáo ra Word và sinh hình từ PlantUML

### 1) Sinh ảnh từ các file `.puml`
Bạn có thể dùng một trong hai cách:

- Cách A (vscode extension): cài “PlantUML” + “Graphviz”. Mở từng file trong `report/diagrams/*.puml`, dùng lệnh Export to PNG/SVG.
- Cách B (CLI): cài Java + Graphviz + PlantUML jar, sau đó:
```
java -jar plantuml.jar report/diagrams/*.puml
```
Ảnh PNG/SVG sẽ xuất cạnh file `.puml`.

### 2) Xuất báo cáo Markdown sang Word (.docx)
- Cài Pandoc.
- Chạy lệnh (PowerShell):
```
pandoc report/Module3_Report.md -o report/Module3_Report.docx \
  --resource-path=.:report:report/diagrams \
  --from markdown+emoji \
  --toc --toc-depth=3
```
Sau đó mở `report/Module3_Report.docx` để chỉnh sửa/điền thông tin cá nhân rồi in.

Mẹo: Chèn các ảnh sơ đồ đã export vào file Word ở các mục tương ứng.


