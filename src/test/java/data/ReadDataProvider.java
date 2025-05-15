package data;

import com.jayway.jsonpath.JsonPath;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReadDataProvider {
    @DataProvider(name = "AccountAndNotificationData")
    public Object[][] getAccountAndNotificationData() throws IOException {
        // 1. Đọc file json
        File accountFile = new File(System.getProperty("user.dir") + "/src/test/resources/account.json");
        File notificationFile = new File(System.getProperty("user.dir") + "/src/test/resources/notification.json");

        // 2. Lấy danh sách thông báo
        List<Map<String, Object>> thongBaoList = JsonPath.read(notificationFile, "$.thongBao[*]");
        // 3. Lấy danh sách acc admin+thuKy và kết hợp
        List<Map<String, Object>> adminAccounts = JsonPath.read(accountFile, "$.acc.admin[*]");
        List<Map<String, Object>> thuKyAccounts = JsonPath.read(accountFile, "$.acc.thuKy[*]");
        List<Map<String, Object>> allAccounts = new ArrayList<>();
        allAccounts.addAll(adminAccounts);
        allAccounts.addAll(thuKyAccounts);

        // 4. Tạo danh sách kết hợp acc + thongBao
        List<Object[]> combinedData = new ArrayList<>();

        for (Map<String, Object> account : allAccounts) {
            for (Map<String, Object> thongBao : thongBaoList) {
                combinedData.add(new Object[]{
                        account.get("user"),
                        account.get("pass"),
                        thongBao.get("tieuDe"),
                        thongBao.get("noiDung")
                });
            }
        }
        return combinedData.toArray(new Object[0][0]);
    }

    @DataProvider(name = "AccountData")
    public Object[][] getAccountData() throws IOException {
        File accountFile = new File(System.getProperty("user.dir") + "/src/test/resources/account.json");

        // 1. Lấy danh sách acc admin+thuKy
        List<Map<String, Object>> adminAccounts = JsonPath.read(accountFile, "$.acc.admin[*]");
        List<Map<String, Object>> thuKyAccounts = JsonPath.read(accountFile, "$.acc.thuKy[*]");
        List<Map<String, Object>> allAccounts = new ArrayList<>();
        allAccounts.addAll(adminAccounts);
        allAccounts.addAll(thuKyAccounts);

        // 2. Tạo dữ liệu chỉ gồm user và pass
        List<Object[]> data = new ArrayList<>();
        for (Map<String, Object> account : allAccounts) {
            data.add(new Object[]{
                    account.get("user"),
                    account.get("pass")
            });
        }
        return data.toArray(new Object[0][0]);
    }

    @DataProvider(name = "AccountAndFilterStatusData")
    public Object[][] getAccountAndFilterStatusData() throws IOException {
        // 1. Đọc file json
        File accountFile = new File(System.getProperty("user.dir") + "/src/test/resources/account.json");
        File filterFile = new File(System.getProperty("user.dir") + "/src/test/resources/filter.json");

        // 2. Lấy danh sách điều kiện lọc trạng thái
        List<Map<String, Object>> trangThaiList = JsonPath.read(filterFile, "$.trangThai[*]");
        // 3. Lấy danh sách acc admin+thuKy và kết hợp
        List<Map<String, Object>> adminAccounts = JsonPath.read(accountFile, "$.acc.admin[*]");
        List<Map<String, Object>> thuKyAccounts = JsonPath.read(accountFile, "$.acc.thuKy[*]");
        List<Map<String, Object>> allAccounts = new ArrayList<>();
        allAccounts.addAll(adminAccounts);
        allAccounts.addAll(thuKyAccounts);

        // 4. Tạo danh sách kết hợp acc + điều kiện lọc trạng thái
        List<Object[]> combinedData = new ArrayList<>();

        for (Map<String, Object> account : allAccounts) {
            for (Map<String, Object> trangThai : trangThaiList) {
                combinedData.add(new Object[]{
                        account.get("user"),
                        account.get("pass"),
                        trangThai.get("value"),
                        trangThai.get("label")
                });
            }
        }
        return combinedData.toArray(new Object[0][0]);
    }

    @DataProvider(name = "AccountAndFilterPersonCreateData")
    public Object[][] AccountAndFilterNguoiDangData() throws IOException {
        // 1. Đọc file json
        File accountFile = new File(System.getProperty("user.dir") + "/src/test/resources/account.json");
        File filterFile = new File(System.getProperty("user.dir") + "/src/test/resources/filter.json");

        // 2. Lấy danh sách điều kiện lọc người tạo
        List<Map<String, Object>> nguoiTaoList = JsonPath.read(filterFile, "$.nguoiTao[*]");
        // 3. Lấy danh sách acc admin+thuKy và kết hợp
        List<Map<String, Object>> adminAccounts = JsonPath.read(accountFile, "$.acc.admin[*]");
        List<Map<String, Object>> thuKyAccounts = JsonPath.read(accountFile, "$.acc.thuKy[*]");
        List<Map<String, Object>> allAccounts = new ArrayList<>();
        allAccounts.addAll(adminAccounts);
        allAccounts.addAll(thuKyAccounts);

        // 4. Tạo danh sách kết hợp acc + điều kiện lọc người tạo
        List<Object[]> combinedData = new ArrayList<>();

        for (Map<String, Object> account : allAccounts) {
            for (Map<String, Object> nguoiTao : nguoiTaoList) {
                combinedData.add(new Object[]{
                        account.get("user"),
                        account.get("pass"),
                        nguoiTao.get("value"),
                        nguoiTao.get("label")
                });
            }
        }
        return combinedData.toArray(new Object[0][0]);
    }
    @DataProvider(name = "AccountAndFAQData")
    public Object[][] getAccountAndFAQData() throws IOException {
        // 1. Đọc file json
        File accountFile = new File(System.getProperty("user.dir") + "/src/test/resources/account.json");
        File notificationFile = new File(System.getProperty("user.dir") + "/src/test/resources/faq.json");

        // 2. Lấy danh sách câu hỏi thường gặp
        List<Map<String, Object>> faqList = JsonPath.read(notificationFile, "$.FAQ[*]");
        // 3. Lấy danh sách acc admin+thuKy và kết hợp
        List<Map<String, Object>> adminAccounts = JsonPath.read(accountFile, "$.acc.admin[*]");
        List<Map<String, Object>> thuKyAccounts = JsonPath.read(accountFile, "$.acc.thuKy[*]");
        List<Map<String, Object>> allAccounts = new ArrayList<>();
        allAccounts.addAll(adminAccounts);
        allAccounts.addAll(thuKyAccounts);

        // 4. Tạo danh sách kết hợp acc + câu hỏi thường gặp
        List<Object[]> combinedData = new ArrayList<>();

        for (Map<String, Object> account : allAccounts) {
            for (Map<String, Object> faq : faqList) {
                combinedData.add(new Object[]{
                        account.get("user"),
                        account.get("pass"),
                        faq.get("cauHoi"),
                        faq.get("cauTraLoi")
                });
            }
        }
        return combinedData.toArray(new Object[0][0]);
    }
    @DataProvider(name = "AccountAdminAndAccValid")
    public Object[][] getAccountAdminAndAccValid() throws IOException {
        // 1. Đọc file json
        File accountFile = new File(System.getProperty("user.dir") + "/src/test/resources/account.json");
        File createaccFile = new File(System.getProperty("user.dir") + "/src/test/resources/createacc.json");

        // 2. Lấy danh sách tạo acc hợp lệ
        List<Map<String, Object>> accValidList = JsonPath.read(createaccFile, "$.accValid[*]");
        // 3. Lấy danh sách acc admin
        List<Map<String, Object>> adminAccounts = JsonPath.read(accountFile, "$.acc.admin[*]");

        // 4. Tạo danh sách kết hợp acc + tạo acc hợp lệ
        List<Object[]> combinedData = new ArrayList<>();

        for (Map<String, Object> account : adminAccounts) {
            for (Map<String, Object> accValid : accValidList) {
                combinedData.add(new Object[]{
                        account.get("user"),
                        account.get("pass"),
                        accValid.get("tenHienThi"),
                        accValid.get("tenDangNhap"),
                        accValid.get("vaiTro"),
                        accValid.get("phongBan"),
                        accValid.get("soDienThoai"),
                        accValid.get("email"),
                        accValid.get("trangThai"),
                        accValid.get("matKhau"),
                        accValid.get("nhapLaiMatKhau")
                });
            }
        }
        return combinedData.toArray(new Object[0][0]);
    }
}
