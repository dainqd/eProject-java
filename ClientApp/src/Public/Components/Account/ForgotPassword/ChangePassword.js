import React, {useEffect} from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Form, Input, message } from 'antd';
import authService from '../../Service/AuthService';
import Background from '../images/bg.jpg';

function ChangePassword() {
    const navigate = useNavigate();

    let email = localStorage.getItem("email")

    const checkLogin = async () => {
        if (email == null){
            navigate('/forgot-password')
        }
    };

    useEffect(() => {
        checkLogin();
    }, []);

    const onFinish = async (values) => {
        let data = {
            code: values.code,
            email: localStorage.getItem('email'),
            username: "string",
            password: values.password,
            confirmPassword: values.confirmPassword
        }
        await authService.changePasswordForgot(data)
            .then((res) => {
                console.log("change-password", res.data)
                localStorage.clear();
                message.success("Change password account success! Please login to continue...")
                navigate("/login")
            })
            .catch((err) => {
                console.log(err)
                message.error("Please check password!")
            })
    };

    return (
        <body className="img-full-screen" style={{backgroundImage: `url(${Background})`}} >
        <div id='login-form'>
            <section className="ftco-section">
                <div className="container">
                    <div className="row justify-content-center">
                        <div className="col-md-6 text-center mb-5">
                            <h2 className="heading-section">Change Password</h2>
                        </div>
                    </div>
                    <div className="row justify-content-center">
                        <div className="col-md-6 col-lg-4">
                            <div className="login-wrap p-0">
                                <Form className="signin-form" onFinish={onFinish} autoComplete="off">
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
                                        <Form.Item
                                            name="password"
                                            rules={[
                                                {
                                                    required: true,
                                                    message: 'Please input your new password!',
                                                }
                                            ]}
                                        >
                                            <Input allowClear type="password"  placeholder="New Password" />
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
                                                    message: 'Please input your new password confirm!',
                                                }
                                            ]}
                                        >
                                            <Input allowClear  type="password"  placeholder="New Password Confirm" />
                                            {/*<span toggle="#password-confirm"*/}
                                            {/*      className="fa fa-fw fa-eye field-icon toggle-password"></span>*/}
                                        </Form.Item>
                                    </div>
                                    <div className="form-group">
                                        <button type="submit"
                                                className="form-control btn btn-primary submit px-3">Submit
                                        </button>
                                    </div>
                                    <div className="form-group d-md-flex">
                                        <div className="w-50 text-md-left ml-2">
                                            <Link to="/" style={{color:"#fff"}}>Back to HOME</Link>
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

export default ChangePassword
