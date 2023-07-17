import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Form, Input, message } from 'antd';
import authService from '../../Service/AuthService';
import '../css/auth.scss'
import Background from '../images/bg.jpg';

function Login() {
    const navigate = useNavigate();

    const onFinish = async (values) => {
        let data = {
            username: values.username,
            password: values.password
        }
        await authService.loginAccount(data)
            .then((res) => {
                console.log("login", res.data)
                sessionStorage.setItem("accessToken", res.data.token);
                sessionStorage.setItem("id", res.data.id);
                sessionStorage.setItem("username", res.data.username);
                message.success(`Welcome ${res.data.username} !`)
                navigate("/dashboard")
            })
            .catch((err) => {
                console.log(err)
                message.error("Please check account!")
            })
    };

    return (
            <body className="img-full-screen img" style={{backgroundImage: `url(${Background})`}} >
            <div id='login-form'>
            <section className="ftco-section">
                <div className="container">
                    <div className="row justify-content-center">
                        <div className="col-md-6 text-center mb-5">
                            <h2 className="heading-section">Login</h2>
                        </div>
                    </div>
                    <div className="row justify-content-center">
                        <div className="col-md-6 col-lg-4">
                            <div className="login-wrap p-0">
                                <Form className="signin-form" onFinish={onFinish} autoComplete="off">
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
                                        <button type="submit"
                                                className="form-control btn btn-primary submit px-3">Login
                                        </button>
                                    </div>
                                    <div className="form-group d-flex mt-5 justify-content-between align-items-center">
                                        <div className="">
                                            <Link className="" to="/forgot-password" style={{color:"#fff", textDecoration:"none"}}>Forgot Password</Link>
                                        </div>
                                        <div className="text-white">
                                            <Link to="/register" style={{color:"#fff", textDecoration:"none", marginRight:"0px"}}>Register</Link>
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

export default Login
