package com.changgou.gateway.filter;


public class UrlFilter {
    // 不需要拦截的url
    private static final String URLs = "/user/login,/api/user/add";

    /**
     * 检查请求路径是否需要权限校验
     *
     * @param uri
     * @return
     */
    public static boolean hasAuthorize(String uri) {
        String[] urls = URLs.split(",");
        for (int i = 0; i < urls.length; i++) {
            if (urls[i].equals(uri)) {
                return false;
            }
        }
        return true;
    }
}
