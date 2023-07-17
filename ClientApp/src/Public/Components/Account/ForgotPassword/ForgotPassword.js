import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Form, Input, message } from 'antd';
import authService from '../../Service/AuthService';
import Background from '../images/bg.jpg';

function ForgotPassword() {
    const navigate = useNavigate();

    const onFinish = async (values) => {
        let data = {
            email: values.email,
            username: "string",
            password: "string",
            confirmPassword: "string"
        }
        await authService.forgotPassword(data)
            .then((res) => {
                console.log("forgot password", res.data)
                localStorage.setItem("email", data.email);
                message.success("Success! Please check your email...")
                navigate("/change-password")
            })
            .catch((err) => {
                console.log(err)
                message.error("Please check account!")
            })
    };

    return (
        <body className="img-full-screen" style={{backgroundImage: `url(${Background})`}} >
        <div id='login-form'>
            <section className="ftco-section">
                <div className="container">
                    <div className="row justify-content-center">
                        <div className="col-md-6 text-center mb-5">
                            <h2 className="heading-section">Forgot password</h2>
                        </div>
                    </div>
                    <div className="row justify-content-center">
                        <div className="col-md-6 col-lg-4">
                            <div className="login-wrap p-0">
                                <Form className="signin-form" onFinish={onFinish} autoComplete="off">
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
                                        <button type="submit"
                                                className="form-control btn btn-primary submit px-3">Submit
                                        </button>
                                    </div>
                                    <div className="form-group d-md-flex">
                                        <div className="w-50 text-md-left ml-2">
                                            <Link to="/login" style={{color:"#fff"}}>Login</Link>
                                        </div>
                                        <div className="w-50 text-md-right mr-2">
                                            <Link to="/register" style={{color:"#fff"}}>Register</Link>
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

export default ForgotPassword
