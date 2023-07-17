import { Form, Input, message } from 'antd';
import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import authService from '../../Service/AuthService';
import Background from "../images/bg.jpg";

function check_pass() {
    if (document.getElementById('password-field').value === document.getElementById('password-confirm').value) {
        document.getElementById('submit').disabled = false;
    } else {
        document.getElementById('submit').disabled = true;
    }
}

function Register() {

    const navigate = useNavigate();
    const onFinish = async (values) => {
        let data = {
            username: values.username,
            email: values.email,
            password: values.password,
            confirmPassword: values.confirmPassword
        }
        await authService.registerAccount(data)
            .then((res) => {
                console.log("register", res.data)
                localStorage.setItem("username", data.username);
                localStorage.setItem("email", data.email);
                localStorage.setItem("password", data.password);
                message.success("Create account success! Please verify account")
                navigate("/register-verify")
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
                        </div>
                    </div>
                    <div className="row justify-content-center">
                        <div className="col-md-6 col-lg-4">
                            <div className="login-wrap p-0">
                                <Form className="signin-form" onFinish={onFinish}>
                                    <div className="form-group">
                                        <Form.Item
                                            name="username"
                                            rules={[
                                                {
                                                    required: true,
                                                    message: 'Please input your username!' ,
                                                }
                                            ]}
                                            hasFeedback
                                        >
                                            <Input allowClear type="text" placeholder="Username" />
                                        </Form.Item>
                                    </div>
                                    <div className="form-group">
                                        <Form.Item
                                            name="email"
                                            rules={[
                                                {
                                                    required: true,
                                                    message: 'Please input your E-mail!',
                                                },
                                                {
                                                    type: "email",
                                                    message: "Invalid E-mail!"
                                                }
                                            ]}
                                            hasFeedback
                                        >
                                            <Input allowClear  placeholder="Email"  />
                                        </Form.Item>
                                    </div>
                                    <div className="form-group">
                                        <Form.Item
                                            name="password"
                                            rules={[
                                                {
                                                    required: true,
                                                    message: 'Please input your password!',
                                                }
                                            ]}
                                        >
                                            <Input allowClear type="password"  placeholder="Password" />
                                            {/*<span toggle="#password-field"*/}
                                            {/*      className="fa fa-fw fa-eye field-icon toggle-password"></span>*/}
                                        </Form.Item>
                                    </div>
                                    <div className="form-group">
                                        <Form.Item
                                            name="confirmPassword"
                                            rules={[
                                                {
                                                    required: true,
                                                    message: 'Please input your password confirm!',
                                                }
                                            ]}
                                        >
                                            <Input allowClear type="password"  placeholder="Password Confirm" />
                                            {/*<span toggle="#password-confirm"*/}
                                            {/*      className="fa fa-fw fa-eye field-icon toggle-password"></span>*/}
                                        </Form.Item>
                                    </div>
                                    <div className="form-group">
                                        <button id="submit" type="submit"
                                                className="form-control btn btn-primary submit px-3">Register
                                        </button>
                                    </div>
                                    <div className="form-group d-md-flex">
                                        <div className="w-50 text-md-left ml-2">
                                            <Link to="/forgot-password" style={{color:"#fff"}}>Forgot Password</Link>
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

export default Register
