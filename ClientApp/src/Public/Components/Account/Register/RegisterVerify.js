import { Form, Input, message } from 'antd';
import React, {useEffect} from 'react';
import { Link, useNavigate } from 'react-router-dom';
import authService from '../../Service/AuthService';
import Background from "../images/bg.jpg";

function RegisterVerify() {
    const navigate = useNavigate();

    let AuthName = localStorage.getItem("username")

    const checkLogin = async () => {
        if (AuthName == null){
            navigate('/register')
        }
    };

    useEffect(() => {
        checkLogin();
    }, []);

    const onFinish = async (values) => {
        let data = {
            username: localStorage.getItem("username"),
            email: localStorage.getItem("email"),
            password: localStorage.getItem("password"),
            confirmPassword: localStorage.getItem("password"),
            code: values.code,
        }
        await authService.registerVerify(data)
            .then((res) => {
                console.log("register verify", res.data)
                localStorage.clear();
                message.success("Create account success! Please login to continue...")
                navigate("/login")
            })
            .catch((err) => {
                console.log(err)
                message.error("Create error")
            })
    };

    return (
        <body className="img-full-screen" style={{backgroundImage: `url(${Background})`}} >
        <div id='register-page'>
            <section className="ftco-section">
                <div className="container">
                    <div className="row justify-content-center">
                        <div className="col-md-6 text-center mb-5">
                            <h2 className="heading-section">Register</h2>
                            <h4 className="text-center text-white">Hello {localStorage.getItem("email")}!</h4>
                        </div>
                    </div>
                    <div className="row justify-content-center">
                        <div className="col-md-6 col-lg-4">
                            <div className="login-wrap p-0">
                                <Form className="signin-form" onFinish={onFinish}>
                                    <div className="form-group">
                                        <Form.Item
                                            name="code"
                                            rules={[
                                                {
                                                    required: true,
                                                    message: 'Please input your code!' ,
                                                }
                                            ]}
                                            hasFeedback
                                        >
                                            <Input allowClear type="text" placeholder="Verify Code" />
                                        </Form.Item>
                                    </div>
                                    <div className="form-group">
                                        <button id="submit" type="submit"
                                                className="form-control btn btn-primary submit px-3">Send
                                        </button>
                                    </div>
                                    <div className="form-group d-md-flex">
                                        <div className="w-50 text-md-left ml-2">
                                            <Link to="/" style={{color:"#fff"}}>Back to Home</Link>
                                        </div>
                                        <div className="w-50 text-md-right mr-2">
                                            <Link to="/login" style={{color:"#fff"}}>Login</Link>
                                        </div>
                                    </div>
                                </Form>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
        </body>
    )
}

export default RegisterVerify
