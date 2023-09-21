package com.example.geossms.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.geossms.common.Constants;
import com.example.geossms.common.Result;
import com.example.geossms.entity.Files;
import com.example.geossms.entity.ParamsForm;
import com.example.geossms.entity.PredictParams;
import com.example.geossms.mapper.FileMapper;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * 文件上传相关接口
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Value("${server.ip}")
    private String serverIp;

    @Value("${server.port}")
    private String serverPort;



    @Value(("${server.password}"))
    private String serverPassword;

    @Resource
    private FileMapper fileMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${cmd.start}")
    private String cmdStart;

    /**
     * 文件上传接口
     * @param file 前端传递过来的文件
     * @return
     * @throws IOException
     */
    @PostMapping("/upload2")
    public String upload2(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        // 定义一个文件唯一的标识码
        String fileUUID = IdUtil.fastSimpleUUID() + StrUtil.DOT + type;

        File uploadFile = new File(fileUploadPath + fileUUID);
        // 判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        File parentFile = uploadFile.getParentFile();
        if(!parentFile.exists()) {
            parentFile.mkdirs();
        }


        String url;
        // 获取文件的md5
        String md5 = SecureUtil.md5(file.getInputStream());
        // 从数据库查询是否存在相同的记录
        Files dbFiles = getFileByMd5(md5);
        if (dbFiles != null) {
            url = dbFiles.getUrl();
        } else {
            // 上传文件到磁盘
            file.transferTo(uploadFile);
            // 数据库若不存在重复文件，则不删除刚才上传的文件
            url = "http://" + serverIp + ":7071/file/" + fileUUID;
        }


        // 存储数据库
        Files saveFile = new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size/1024); // 单位 kb
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        saveFile.setIsDelete(false);
        saveFile.setEnable(true);
        fileMapper.insert(saveFile);

        // 从redis取出数据，操作完，再设置（不用查询数据库）
//        String json = stringRedisTemplate.opsForValue().get(Constants.FILES_KEY);
//        List<Files> files1 = JSONUtil.toBean(json, new TypeReference<List<Files>>() {
//        }, true);
//        files1.add(saveFile);
//        setCache(Constants.FILES_KEY, JSONUtil.toJsonStr(files1));


        // 从数据库查出数据
//        List<Files> files = fileMapper.selectList(null);
//        // 设置最新的缓存
//        setCache(Constants.FILES_KEY, JSONUtil.toJsonStr(files));

        // 最简单的方式：直接清空缓存
        flushRedis(Constants.FILES_KEY);

        return url;
    }

    /**
     * 文件上传接口
     *
     * @param file 前端传递过来的文件
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            // 处理文件为空的情况
            return "redirect:/uploadError";
        }

        // 获取文件的原始名称
        String originalFilename = file.getOriginalFilename();
        System.out.println("originalFilename: " + originalFilename);

        // 获取文件的类型，以“.”作为标识
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        // 定义一个文件唯一的标识码
        String fileUUID = IdUtil.fastSimpleUUID() + StrUtil.DOT + type;
        System.out.println("fileUUID: " + fileUUID);

        // 上传文件的路径
        File uploadFile = new File(fileUploadPath + "/infile/" + originalFilename);


        // 创建JSON文件夹
//        String jsonFolderPath = fileUploadPath + "/";
//        File jsonFolder = new File(jsonFolderPath);
//        if (!jsonFolder.exists()) {
//            boolean created = jsonFolder.mkdirs();
//            if (!created) {
//                // JSON文件夹创建失败，处理异常情况
//                // 可以抛出异常或打印错误日志
//                System.err.println("Failed to create JSON folder.");
//            }
//        }
//        String jsonFilePath = jsonFolderPath + FileUtil.mainName(originalFilename) + ".json";


//        String jsonFilePath = fileUploadPath + "origin/json/" + FileUtil.mainName(originalFilename) + ".json";

        // 判断配置的文件目录是否存在，若不存在则创建一个新的文件目录
        File parentFile = uploadFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }


        String url;
        // 获取文件的md5
        String md5 = SecureUtil.md5(file.getInputStream());
        //String md5 = file.getOriginalFilename();

        // 从数据库查询是否存在相同的记录
        Files dbFiles = getFileByMd5(md5);
        if (dbFiles != null) {
            url = dbFiles.getUrl();
        } else {


            // 上传文件到磁盘
            file.transferTo(uploadFile);

            // 将csv文件转为json文件
//            try (CSVReader reader = new CSVReader(new FileReader(uploadFile))) {
//                List<String[]> csvData = reader.readAll();
//                List<Object> jsonData = new ArrayList<>();
//
//                // Assuming the first row of the CSV file contains column headers
//                String[] headers = csvData.get(0);
//
//                // Convert each row to a JSON object
//                for (int i = 1; i < csvData.size(); i++) {
//                    String[] row = csvData.get(i);
//                    // Create a Map or a custom class to represent each row as JSON
//                    // For simplicity, using a Map here
//                    // You can define your own class and populate its attributes if needed
//                    // Here, we assume that the CSV has the same number of columns as headers
//                    // If not, you need to handle that accordingly
//                    // For example, skipping rows with different lengths or filling with default values
//                    // before converting to JSON
//                    Map<String, String> rowData = new HashMap<>();
//                    for (int j = 0; j < headers.length; j++) {
//                        String header = headers[j];
//                        String value = row[j];
//
//                        // 修改字段名称
//                        if ("ROUND(A.WS,1)".equals(header)) {
//                            header = "AWS";
//                        }
//                        if ("ROUND(A.POWER,0)".equals(header)) {
//                            header = "APOWER";
//                        }
//                        rowData.put(header, value);
//                    }
//                    jsonData.add(rowData);
//                }
//
//                // Convert the JSON data to a JSON string
//                String jsonString = new Gson().toJson(jsonData);
//
//                // Write the JSON string to a file
//                try (FileWriter writer = new FileWriter(jsonFilePath)) {
//                    writer.write(jsonString);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            // 数据库若不存在重复文件，则不删除刚才上传的文件
            url = "http://" + serverIp + ":" + serverPort + "/file/" + originalFilename;
        }


        // 存储数据库
        Files saveFile = new Files();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size / 1024); // 单位 kb
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        fileMapper.insert(saveFile);

        // 从redis取出数据，操作完，再设置（不用查询数据库）
//        String json = stringRedisTemplate.opsForValue().get(Constants.FILES_KEY);
//        List<Files> files1 = JSONUtil.toBean(json, new TypeReference<List<Files>>() {
//        }, true);
//        files1.add(saveFile);
//        setCache(Constants.FILES_KEY, JSONUtil.toJsonStr(files1));


        // 从数据库查出数据
//        List<Files> files = fileMapper.selectList(null);
//        // 设置最新的缓存
//        setCache(Constants.FILES_KEY, JSONUtil.toJsonStr(files));

        // 最简单的方式：直接清空缓存
        flushRedis(Constants.FILES_KEY);

        return url;
    }


    /**
     * 获取原始json文件内容
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    @PostMapping("/origin/json")
    public ResponseEntity<Result> getOriginJson(@RequestBody String fileName) throws IOException {
        System.out.println("fileName = " + fileName);
        String filePath = fileUploadPath + "/infile/" + fileName;  // 替换为实际的文件路径
        System.out.println("filePath = " + filePath);

        // 读取文件内容
        Path jsonFilePath = Paths.get(filePath);
        byte[] fileBytes = java.nio.file.Files.readAllBytes(jsonFilePath);
        String jsonContent = new String(fileBytes);


        Result result = new Result();
        result.setJsonContent(jsonContent);
        result.setCode(Constants.CODE_200);

        // 返回结果
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     * TODO 获取预处理后json文件内容
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    @PostMapping("/processed/json")
    public ResponseEntity<Result> getProcessedJson(@RequestBody String fileName) throws IOException {
        System.out.println("fileName = " + fileName);
        String filePath = fileUploadPath + "/outfile/" + fileName;  // 替换为实际的文件路径
        System.out.println("filePath = " + filePath);

        // 读取文件内容
        Path jsonFilePath = Paths.get(filePath);
        byte[] fileBytes = java.nio.file.Files.readAllBytes(jsonFilePath);
        String jsonContent = new String(fileBytes);


        Result result = new Result();
        result.setJsonContent(jsonContent);
        result.setCode(Constants.CODE_200);
        // 返回结果
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     * TODO 获取预测后json文件内容
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    @PostMapping("/predicted/json")
    public ResponseEntity<Result> getPredictedJson(@RequestBody String fileName) throws IOException {
        System.out.println("fileName = " + fileName);
        String filePath = fileUploadPath + "/pred/" + fileName;  // 替换为实际的文件路径
        System.out.println("filePath = " + filePath);

        // 读取文件内容
        Path jsonFilePath = Paths.get(filePath);
        byte[] fileBytes = java.nio.file.Files.readAllBytes(jsonFilePath);
        String jsonContent = new String(fileBytes);


        Result result = new Result();
        result.setJsonContent(jsonContent);
        result.setCode(Constants.CODE_200);
        // 返回结果
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 文件下载接口   http://localhost:9090/file/{fileUUID}
     * @param fileUUID
     * @param response
     * @throws IOException
     */
    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath + fileUUID);
        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");

        // 读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();
        os.close();
    }


    /**
     * @param fileName
     * @param response
     * @throws IOException
     */
    @GetMapping("/infile/{fileName}")
    public void downloadInFile(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath +"/infile/"+fileName);
        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();//获取输出流
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));//设置文件名
        response.setContentType("application/octet-stream");//设置类型

        // 读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();//刷新
        os.close();//关闭
    }


    /**
     *
     * @param fileName
     * @param response
     * @throws IOException
     */
    @GetMapping("/outfile/{fileName}")
    public void downloadOutFile(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath +"/outfile/"+fileName);
        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();//获取输出流
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));//设置文件名
        response.setContentType("application/octet-stream");//设置类型

        // 读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();//刷新
        os.close();//关闭
    }


    /**
     *
     * @param fileName
     * @param response
     * @throws IOException
     */
    @GetMapping("/pred/{fileName}")
    public void downloadPred(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File uploadFile = new File(fileUploadPath +"/pred/"+fileName);
        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();//获取输出流
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));//设置文件名
        response.setContentType("application/octet-stream");//设置类型

        // 读取文件的字节流
        os.write(FileUtil.readBytes(uploadFile));
        os.flush();//刷新
        os.close();//关闭
    }


    /**
     * 通过文件的md5查询文件
     *
     * @param md5
     * @return
     */
    private Files getFileByMd5(String md5) {
        // 查询文件的md5是否存在
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
        List<Files> filesList = fileMapper.selectList(queryWrapper);
        return filesList.size() == 0 ? null : filesList.get(0);
    }

    //    @CachePut(value = "files", key = "'frontAll'")
    @PostMapping("/update")
    public Result update(@RequestBody Files files) {
        fileMapper.updateById(files);
        flushRedis(Constants.FILES_KEY);
        return Result.success();
    }

    @GetMapping("/detail/{id}")
    public Result getById(@PathVariable Integer id) {
        return Result.success(fileMapper.selectById(id));
    }

    //清除一条缓存，key为要清空的数据
//    @CacheEvict(value="files",key="'frontAll'")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        Files files = fileMapper.selectById(id);
        files.setIsDelete(true);
        fileMapper.updateById(files);
        flushRedis(Constants.FILES_KEY);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        // select * from sys_file where id in (id,id,id...)
        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        List<Files> files = fileMapper.selectList(queryWrapper);
        for (Files file : files) {
            file.setIsDelete(true);
            fileMapper.updateById(file);
        }
        return Result.success();
    }

    /**
     * 分页查询接口
     *
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name) {

        QueryWrapper<Files> queryWrapper = new QueryWrapper<>();
        // 查询未删除的记录
        queryWrapper.eq("is_delete", false);
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
        return Result.success(fileMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper));
    }

    // 设置缓存
    private void setCache(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    // 删除缓存
    private void flushRedis(String key) {
        stringRedisTemplate.delete(key);
    }


    /**
     * 预处理
     *
     * @param paramsForm
     * @return
     */
    @PostMapping("/preprocess")
    public Result preprocess(@RequestBody ParamsForm paramsForm) {

        System.out.println("预处理的文件：" + paramsForm.getFileName());

        String host = serverIp; // 远程服务器IP地址
        String user = "root"; // 远程服务器用户名
        String password = serverPassword; // 远程服务器密码



        if(host.equals("localhost")){
            String command = "conda activate py37 && cd " + fileUploadPath + " && python data_preprocess.py --file_name " + paramsForm.getFileName() + " --resample_method " + paramsForm.getResampleMethod() + " --outlier_detection " + paramsForm.getOutlierDetection();

            try {
                ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
                processBuilder.redirectErrorStream(true);

                Process process = processBuilder.start();

                InputStream inputStream = process.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line); // 输出结果到控制台
                }

                int exitCode = process.waitFor(); // 等待命令执行完成
                System.out.println("exit-status: " + exitCode); // 输出退出状态

            } catch (Exception e) {
                e.printStackTrace(); // 输出错误信息
            }

        }else {
            // 要执行的命令
            System.out.println("conda activate py37;cd " + fileUploadPath + ";python data_preprocess.py --file_name " + paramsForm.getFileName() + " --resample_method " + paramsForm.getResampleMethod() + " --outlier_detection " + paramsForm.getOutlierDetection());
            StringBuilder command = new StringBuilder("conda activate py37;cd " + fileUploadPath + ";python data_preprocess.py --file_name " + paramsForm.getFileName() + " --resample_method " + paramsForm.getResampleMethod() + " --outlier_detection " + paramsForm.getOutlierDetection());
//        command.append("ls -la;");


            try {
                JSch jsch = new JSch();
                Session session = jsch.getSession(user, host, 22); // 创建一个SSH会话
                session.setPassword(password); // 设置会话密码
                session.setConfig("StrictHostKeyChecking", "no"); // 设置会话配置,不检查HostKey
                session.connect(); // 连接会话

                Channel channel = session.openChannel("exec"); // 打开一个exec通道
                ((ChannelExec) channel).setCommand(command.toString()); // 设置要执行的命令
                channel.setInputStream(null);
                ((ChannelExec) channel).setErrStream(System.err); // 设置错误输出流

                InputStream inputStream = channel.getInputStream();
                channel.connect(); // 连接通道

                byte[] buffer = new byte[10240];
                while (true) {
                    while (inputStream.available() > 0) {
                        int i = inputStream.read(buffer, 0, 10240);
                        if (i < 0) {
                            break;
                        }
                        System.out.print(new String(buffer, 0, i)); // 输出结果到控制台
                    }
                    if (channel.isClosed()) {
                        if (inputStream.available() > 0) {
                            continue;
                        }
                        System.out.println("exit-status: " + channel.getExitStatus()); // 输出退出状态
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (Exception ee) {
                    } // 等待一秒钟
                }
                channel.disconnect(); // 断开通道
                session.disconnect(); // 断开会话
            } catch (Exception e) {
                e.printStackTrace(); // 输出错误信息
            }
        }

        System.out.println("预处理完成");
        return Result.success();
    }



    /**
     * 预测
     *
     * @param predictParams
     * @return
     */
    @PostMapping("/predict")
    public Result predict(@RequestBody PredictParams predictParams) {

        System.out.println("预测的文件：" + predictParams.getFileName());

        String host = serverIp; // 远程服务器IP地址
        String user = "root"; // 远程服务器用户名
        String password = serverPassword; // 远程服务器密码


        if(host.equals("localhost")){
            String command = "conda activate py37 && cd " + fileUploadPath + " && python predict.py --file_name " + predictParams.getFileName() + " --start_time " + predictParams.getStartTime() + " --end_time " + predictParams.getEndTime();

            try {
                ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
                processBuilder.redirectErrorStream(true);

                Process process = processBuilder.start();

                InputStream inputStream = process.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line); // 输出结果到控制台
                }

                int exitCode = process.waitFor(); // 等待命令执行完成
                System.out.println("exit-status: " + exitCode); // 输出退出状态

            } catch (Exception e) {
                e.printStackTrace(); // 输出错误信息
            }
        }else {
            // 要执行的命令
            StringBuilder command = new StringBuilder("conda activate py37;cd " + fileUploadPath + ";python predict.py --file_name " + predictParams.getFileName() + " --start_time " + predictParams.getStartTime() + " --end_time " + predictParams.getEndTime());
//        command.append("ls -la;");

            try {
                JSch jsch = new JSch();
                Session session = jsch.getSession(user, host, 22); // 创建一个SSH会话
                session.setPassword(password); // 设置会话密码
                session.setConfig("StrictHostKeyChecking", "no"); // 设置会话配置,不检查HostKey
                session.connect(); // 连接会话

                Channel channel = session.openChannel("exec"); // 打开一个exec通道
                ((ChannelExec) channel).setCommand(command.toString()); // 设置要执行的命令
                channel.setInputStream(null);
                ((ChannelExec) channel).setErrStream(System.err); // 设置错误输出流

                InputStream inputStream = channel.getInputStream();
                channel.connect(); // 连接通道

                byte[] buffer = new byte[1024];
                while (true) {
                    while (inputStream.available() > 0) {
                        int i = inputStream.read(buffer, 0, 1024);
                        if (i < 0) {
                            break;
                        }
                        System.out.print(new String(buffer, 0, i)); // 输出结果到控制台
                    }
                    if (channel.isClosed()) {
                        if (inputStream.available() > 0) {
                            continue;
                        }
                        System.out.println("exit-status: " + channel.getExitStatus()); // 输出退出状态
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (Exception ee) {
                    } // 等待一秒钟
                }
                channel.disconnect(); // 断开通道
                session.disconnect(); // 断开会话
            } catch (Exception e) {
                e.printStackTrace(); // 输出错误信息
            }
        }

        return Result.success();
    }

}
