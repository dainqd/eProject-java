import {Link, useNavigate, useParams} from "react-router-dom";
import {Button, Form, Input, message} from "antd";
import React, {useEffect, useState} from "react";
import Header from "../../../Shared/Admin/Header/Header";
import Sidebar from "../../../Shared/Admin/Sidebar/Sidebar";

// function DetailFeedback() {
//     const navigate = useNavigate();
//     const { id } = useParams();
//     const [form] = Form.useForm();
//     const [feedback, setData] = useState([]);
//
//     useEffect(() => {
//         const detailsInsurance = async () => {
//             await feedbackService.detailFeedback(id)
//                 .then((res) => {
//                     console.log("details feedback", res.data);
//                     form.setFieldsValue({ id: res.data.id })
//                     form.setFieldsValue({ fullname: res.data.fullname })
//                     form.setFieldsValue({ email: res.data.email });
//                     form.setFieldsValue({ message: res.data.message })
//                     form.setFieldsValue({ status: res.data.status });
//                     setData(res.data);
//
//                 })
//                 .catch((err) => {
//                     console.log(err)
//                 })
//         };
//         detailsInsurance();
//     }, [form, id])
//
//     const UpdateFeedback = async () => {
//
//         var status = document.getElementById("status").value;
//         console.log(status);
//
//         await feedbackService.updateFeedback(id, status)
//             .then((res) => {
//                 console.log("update", id, status)
//                 alert("Update Feedback success!")
//                 navigate('/feedback/list')
//             })
//             .catch((err) => {
//                 console.log(err)
//                 message.error("Error! Please try again...")
//             })
//     };
//
//     const status = [
//         {
//             id: "APPROVED",
//             type: "APPROVED"
//         },
//         {
//             id: "PENDING",
//             type: "PENDING"
//         },
//         {
//             id: "REFUSE",
//             type: "REFUSE"
//         },
//         {
//             id: "COMPLETE",
//             type: "COMPLETE"
//         },
//
//     ]
//
//     return (
//         <>
//             <Header />
//             <Sidebar />
//             <main id="main" className="main" style={{backgroundColor:"#f6f9ff"}}>
//                 <div className="pagetitle">
//                     <h1>Detail Feedback</h1>
//                     <nav>
//                         <ol className="breadcrumb">
//                             <li className="breadcrumb-item"><Link to="/dashboard">Dashboard</Link></li>
//                             <li className="breadcrumb-item">Feedback</li>
//                             <li className="breadcrumb-item active">Detail Feedback</li>
//                         </ol>
//                     </nav>
//                 </div>{/* End Page Title */}
//                 <section className="section">
//                     <div className="row">
//                         <div className="col-lg-12">
//                             <div className="card">
//                                 <div className="card-body">
//                                     <h5 className="card-title">Detail Feedback</h5>
//                                     <Form className="row"
//                                           form={form}
//                                           labelCol={{
//                                               span: 8,
//                                           }}
//                                           wrapperCol={{
//                                               span: 16,
//                                           }}
//                                           initialValues={{
//                                               remember: true,
//                                           }}
//                                           onFinish={UpdateFeedback}
//                                           autoComplete="off"
//                                     >
//                                         <div className="col-md-2">
//                                             <label>
//                                                 ID
//                                             </label>
//                                             <Form.Item
//                                                 name="id">
//                                                 <Input disabled />
//                                             </Form.Item>
//                                         </div>
//
//                                         <div className="col-md-3">
//                                             <label>
//                                                 FULL NAME
//                                             </label>
//                                             <Form.Item
//                                                 name="fullname">
//                                                 <Input disabled />
//                                             </Form.Item>
//                                         </div>
//
//                                         <div className="col-md-4">
//                                             <label>
//                                                 EMAIL
//                                             </label>
//                                             <Form.Item name="email">
//                                                 <Input disabled/>
//                                             </Form.Item>
//                                         </div>
//
//                                         <div className="col-md-4">
//                                             <label>
//                                                 MESSAGE
//                                             </label>
//                                             <Form.Item
//                                                 name="message">
//                                                 <Input disabled/>
//                                             </Form.Item>
//                                         </div>
//
//                                         <div className="col-md-4">
//                                             <label>
//                                                 STATUS
//                                             </label>
//                                             <Form.Item
//                                                 name="status">
//                                                 <select className="form-select" id="status">
//                                                     {
//                                                         status.map((item, index) => (
//                                                             <option key={index}
//                                                                     selected={feedback.status===item.type ? "selected" : " " }
//                                                             >
//                                                                 {item.type}
//                                                             </option>
//                                                         ))
//                                                     }
//                                                 </select>
//                                             </Form.Item>
//                                         </div>
//                                         <Form.Item
//                                             wrapperCol={{
//                                                 offset: 8,
//                                                 span: 16,
//                                             }}
//                                         >
//                                             <Button type="primary" htmlType="submit">
//                                                 Submit
//                                             </Button>
//                                         </Form.Item>
//                                     </Form>
//                                 </div>
//                             </div>
//                         </div>
//                     </div>
//                 </section>
//             </main>
//         </>
//     )
// }

// export default DetailFeedback