package kr.easw.lesson02.controller;

import kr.easw.lesson02.service.AWSService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class BaseWebController {
    private final AWSService awsController;

    @RequestMapping("/")
    public ModelAndView onIndex() {
        if (awsController.isInitialized()) {
            return new ModelAndView("upload.html");
        }
        return new ModelAndView("request_aws_key.html");
    }

    @RequestMapping("/server-error")
    public ModelAndView onErrorTest() {
        return new ModelAndView("error.html");
    }


    @RequestMapping("/upload")
    public ModelAndView onUpload() {
        if (awsController.isInitialized()) {
            return new ModelAndView("upload.html");
        }
        return new ModelAndView("request_aws_key.html");
    }

    @RequestMapping("/download")
    public ModelAndView onDownload() {
        if (awsController.isInitialized()) {
            return new ModelAndView("download.html");
        }
        return new ModelAndView("request_aws_key.html");
    }
}
