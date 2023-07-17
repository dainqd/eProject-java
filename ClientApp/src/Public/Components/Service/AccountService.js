import { BASE_URL_SERVER } from "../config/server";
import axios from "axios";

const API_ENDPOINT = {
    //
    FIND_USER_EMAIL: "/api/find/user-by-email/",
    FIND_USER_USERNAME: "/api/find/user-by-username/",
    //
    CHANGE_EMAIL: "/api/settings/change-email/",
    CHANGE_EMAIL_VERIFY: "/api/settings/change-email-verify/",
    CHANGE_USERNAME: "/api/settings/change-username/",
    CHANGE_STATUS: "/api/settings/change-status/",
    //
    LIST_ACCOUNT: "/api/user/list",
    DETAIL_ACCOUNT: "/api/user/detail/",
    UPDATE_ACCOUNT: "/api/user/update-info/",
    CHANGE_PASSWORD_ACCOUNT: "/api/user/change-pass/",
    //
    ADMIN_LIST_ACCOUNT: "/admin/api/user/list",
    ADMIN_LIST_STATUS_ACCOUNT: "/admin/api/user/list/",
    ADMIN_DETAIL_ACCOUNT: "/admin/api/user/detail/",
    ADMIN_CREATE_ACCOUNT: "/admin/api/user",
    ADMIN_UPDATE_ACCOUNT: "/admin/api/user/update-info/",
    ADMIN_DELETE_ACCOUNT: "/admin/api/user/",
}
class AccountService {
    // Find
    findUserByEmail = (email) => {
        return axios.get(BASE_URL_SERVER + API_ENDPOINT.FIND_USER_EMAIL + email)
    }

    findUserByUsername = (username) => {
        return axios.get(BASE_URL_SERVER + API_ENDPOINT.FIND_USER_USERNAME + username)
    }
    // api/settings/change-email/1213
    changeEmail = (id, data) => {
        const config = {
            headers: {
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        console.log(config)
        return axios.post(BASE_URL_SERVER + API_ENDPOINT.CHANGE_EMAIL + id + "?email="+ data ,"", config);
    }

    changeEmailVerify = (id, email, code) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.post(BASE_URL_SERVER + API_ENDPOINT.CHANGE_EMAIL_VERIFY + id + "?email="+ email + "&code="+ code,"", config);
    }

    changeUsername = (id, data) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.post(BASE_URL_SERVER + API_ENDPOINT.CHANGE_USERNAME + id + "?username="+ data,"", config);
    }

    changeStatus = (id, data) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.post(BASE_URL_SERVER + API_ENDPOINT.CHANGE_STATUS + id + "?status="+ data,"", config);
    }
    //
    listAccount = () => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.get(BASE_URL_SERVER + API_ENDPOINT.LIST_ACCOUNT, config);
    }

    updateAccount = (id, data) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.put(BASE_URL_SERVER + API_ENDPOINT.UPDATE_ACCOUNT + id, data, config);
    }

    changePassAccount = (id, data) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.put(BASE_URL_SERVER + API_ENDPOINT.CHANGE_PASSWORD_ACCOUNT + id, data, config);
    }

    detailAccount = (id) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.get(BASE_URL_SERVER + API_ENDPOINT.DETAIL_ACCOUNT + id, config)
    }
    // ADMIN
    adminListAccount = () => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.get(BASE_URL_SERVER + API_ENDPOINT.ADMIN_LIST_ACCOUNT, config);
    }

    adminListStatusAccount = (data) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.get(BASE_URL_SERVER + API_ENDPOINT.ADMIN_LIST_STATUS_ACCOUNT + data, config);
    }

    adminDetailAccount = (id) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.get(BASE_URL_SERVER + API_ENDPOINT.ADMIN_DETAIL_ACCOUNT + id, config)
    }

    adminCreateAccount = (data) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.post(BASE_URL_SERVER + API_ENDPOINT.ADMIN_CREATE_ACCOUNT ,data, config);
    }

    adminUpdateAccount = (id, data) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.put(BASE_URL_SERVER + API_ENDPOINT.ADMIN_UPDATE_ACCOUNT + id, data, config);
    }

    adminDeleteAccount = (id) => {
        const config = {
            headers: {
                'content-type': 'application/json',
                'Authorization': `Bearer ${sessionStorage.getItem("accessToken")}`
            }
        };
        return axios.delete(BASE_URL_SERVER + API_ENDPOINT.ADMIN_DELETE_ACCOUNT + id, config)
    }

}
const accountService = new AccountService();
export default accountService;