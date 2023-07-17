// import React, {useEffect, useState} from 'react'
// import { Link } from 'react-router-dom';
// import {Form, message} from "antd";
// import WOW from 'wowjs';
//
//
//
//
// function Home() {
//     // const navigate = useNavigate();
//     const [data, setData] = useState([]);
//     const list = [];
//     const insurance = [];
//
//     const onFinish = async (values) => {
//         var fullName = document.getElementById("fullname").value;
//         var email = document.getElementById("gmail").value;
//         var messages = document.getElementById("message").value;
//         var insuranceId = document.getElementById("insurance").value;
//         var phoneNumber = document.getElementById("phonenumber").value;
//
//         if (fullName == null || email == null || phoneNumber == null || insuranceId == null){
//             message.error("Error, Please try again!")
//         }
//
//         let data = {
//             fullname: fullName,
//             insurance_id: insuranceId,
//             email: email,
//             phone: phoneNumber,
//             message: messages
//         }
//         await appointmentService.createAppointment(data)
//             .then((res) => {
//                 console.log("Appointment", res.data)
//                 alert('Thank you for trusting us!')
//             })
//             .catch((err) => {
//                 console.log(err)
//                 message.error("Error, Please try again!")
//             })
//     };
//
//     const getListInsurance = async () => {
//         await insuranceService.listInsurance()
//             .then((res) => {
//                 if (res.status === 200){
//                     console.log("data", res.data)
//
//                     console.log(res.data[1])
//                     setData(res.data)
//                 } else {
//                     alert('Error')
//                 }
//             })
//             .catch((err) => {
//                 console.log(err)
//                 alert("Error, Please try again!")
//             })
//     };
//
//     useEffect(() => {
//         getListInsurance();
//     }, []);
//
//     data.forEach((insure, index) => {
//         var link = null;
//         link = "/insurances/detail/" + insure.id;
//         list.push(
//             <div className="col-lg-5 col-md-6 wow fadeInUp" data-wow-delay="0.1s" key={index}>
//                 <div className="service-item rounded h-100 p-5">
//                     <div className="d-flex align-items-center ms-n5 mb-4">
//                         <div className="" style={{backgroundColor:"#ccc", marginRight:"25px"}} >
//                         <img className="img-fluid" src={insure.thumbnail} alt="" width="100px" height="100px"
//                              style={{border:"1px solid #ccc", borderRadius:"3px"}}/>
//                         </div>
//                         <h4 className="mb-0">
//                             {insure.name}
//                         </h4>
//                     </div>
//                     <p className="mb-4">
//
//                     </p>
//                     <Link className="btn btn-light px-3" to={link}>Read More</Link>
//                 </div>
//             </div>,
//         );
//     });
//
//     data.forEach((insure, index) => {
//         insurance.push(
//             <option value={insure.id} key={index}>{insure.name}</option>
//         );
//     });
//
//         useEffect(() => {
//                new WOW.WOW().init();
//             }, []);
//
//     return (
//         <div style={{backgroundColor:"#fff"}}>
//
//             <Header />
//             <Navbar />
//             <div className="container-fluid p-0 mb-5 wow fadeIn" data-wow-delay="0.1s">
//                 <div id="header-carousel" className="carousel slide" data-bs-ride="carousel">
//                     <div className="carousel-inner">
//                         <div className="carousel-item active">
//                             {/* eslint-disable-next-line jsx-a11y/img-redundant-alt */}
//                             <img className="w-100" src={img_carousel_1} alt="Image" />
//                             <div className="carousel-caption">
//                                 <div className="container">
//                                     <div className="row">
//                                         <div className="col-12 col-lg-6">
//                                             <h1 className="display-3 text-dark mb-4 animated slideInDown" style={{fontWeight: "bold"}}>
//                                                 Insurance Creates Wealth For Everyone
//                                             </h1>
//                                             <p className="fs-5 text-body mb-5">
//                                                 FiveSuperHero life insurance company offers a variety of
//                                                 financial protection solutions with practical benefits,
//                                                 quick settlement of insurance benefits.
//                                             </p>
//                                             <Link to="" className="btn btn-primary py-3 px-5"
//                                             >More Details</Link>
//                                         </div>
//                                     </div>
//                                 </div>
//                             </div>
//                         </div>
//                         <div className="carousel-item">
//                             {/* eslint-disable-next-line jsx-a11y/img-redundant-alt */}
//                             <img className="w-100" src={img_carousel_2} alt="Image" />
//                             <div className="carousel-caption">
//                                 <div className="container">
//                                     <div className="row">
//                                         <div className="col-12 col-lg-6">
//                                             <h1 className="display-3 text-dark mb-4 animated slideInDown">
//                                                 The Best Insurance Begins Here
//                                             </h1>
//                                             <p className="fs-5 text-body mb-5">
//                                                 FiveSuperHero life insurance company offers a variety of financial
//                                                 protection solutions with practical benefits,
//                                                 quick settlement of insurance benefits.
//                                             </p>
//                                             <Link to="" className="btn btn-primary py-3 px-5"
//                                             >More Details</Link>
//                                         </div>
//                                     </div>
//                                 </div>
//                             </div>
//                         </div>
//                     </div>
//                     <button
//                         className="carousel-control-prev"
//                         type="button"
//                         data-bs-target="#header-carousel"
//                         data-bs-slide="prev"
//                     >
//                         <span className="carousel-control-prev-icon" aria-hidden="true"></span>
//                         <span className="visually-hidden">Previous</span>
//                     </button>
//                     <button
//                         className="carousel-control-next"
//                         type="button"
//                         data-bs-target="#header-carousel"
//                         data-bs-slide="next"
//                     >
//                         <span className="carousel-control-next-icon" aria-hidden="true"></span>
//                         <span className="visually-hidden">Next</span>
//                     </button>
//                 </div>
//             </div>
//
//             <div className="container-xxl py-5" >
//                 <div className="container">
//                     <div className="row g-5" >
//                         <div className="col-lg-6 wow fadeInUp" data-aos="fade-up" data-wow-delay="0.1s">
//                             <div
//                                 className="position-relative overflow-hidden rounded ps-5 pt-5 h-100"
//                                 style={{minHeight: "400px"}}
//                             >
//                                 <img
//                                     className="position-absolute w-100 h-100"
//                                     src={img_about}
//                                     alt=""
//                                     style={{objectFit: "cover"}}
//                                 />
//                                 <div
//                                     className="position-absolute top-0 start-0 bg-white rounded pe-3 pb-3"
//                                     style={{width: "200px" ,height: "200px"}}
//                                 >
//                                     <div
//                                         className="d-flex flex-column justify-content-center text-center bg-primary rounded h-100 p-3"
//                                     >
//                                         <h1 className="text-white mb-0">25</h1>
//                                         <h2 className="text-white">Years</h2>
//                                         <h5 className="text-white mb-0">Experience</h5>
//                                     </div>
//                                 </div>
//                             </div>
//                         </div>
//                         <div className="col-lg-6 wow fadeInUp"  data-wow-delay="0.5s">
//                             <div className="h-100">
//                                 <h1 className="display-6 mb-5" style={{fontWeight: "bold"}}>
//                                     We're Here To Assist You With Exploring Protection
//                                 </h1>
//                                 <p className="fs-5 text-primary mb-4">
//                                     At FiveSuperHero, we accompany our clients in making decisions about protecting their health,
//                                     increasing their assets and planning for the future.
//                                     Our digital transformation journey with huge investment in technology to bring innovative hedging solutions
//                                     and services to people so that they enjoy better life every day
//                                 </p>
//                                 <div className="row g-4 mb-4">
//                                     <div className="col-sm-6">
//                                         <div className="d-flex align-items-center">
//                                             <img
//                                                 className="flex-shrink-0 me-3"
//                                                 src={img_icon_4_pri}
//                                                 alt=""
//                                             />
//                                             <h5 className="mb-0">Flexible Insurance Plans</h5>
//                                         </div>
//                                     </div>
//                                     <div className="col-sm-6">
//                                         <div className="d-flex align-items-center">
//                                             <img
//                                                 className="flex-shrink-0 me-3"
//                                                 src={img_icon_3_pri}
//                                                 alt=""
//                                             />
//                                             <h5 className="mb-0">Money Back Guarantee</h5>
//                                         </div>
//                                     </div>
//                                 </div>
//                                 <p className="mb-4">
//                                     With more than 1.5 million customers,
//                                     a team of professional consultants and strategic partners in the distribution
//                                     of insurance products and a network of 8000 offices around the world,
//                                     FiveSuperHero is proud of its 23-year history as one of the leading
//                                     insurance companies world -
//                                     Life insurance companies are trusted by the whole world.
//                                 </p>
//                                 <div className="border-top mt-4 pt-4">
//                                     <div className="d-flex align-items-center">
//                                         <img
//                                             className="flex-shrink-0 rounded-circle me-3"
//                                             src={img_profile}
//                                             alt=""
//                                         />
//                                         <h5 className="mb-0">Call Us: +012 345 6789</h5>
//                                     </div>
//                                 </div>
//                             </div>
//                         </div>
//                     </div>
//                 </div>
//             </div>
//
//             <div className="container-fluid overflow-hidden my-5 px-lg-0">
//                 <div className="container facts px-lg-0">
//                     <div className="row g-0 mx-lg-0">
//                         <div className="col-lg-6 facts-text wow fadeIn" data-wow-delay="0.1s">
//                             <div className="h-100 px-4 ps-lg-0">
//                                 <h1 className="text-white mb-4" style={{fontWeight: "bold"}}> FiveSuperHero Financial Group</h1>
//                                 <p className="text-light mb-5">
//                                     FiveSuperHero Financial Group is one of the world's
//                                     leading corporations in providing financial services,
//                                     operating with a mission to help clients "Make Decisions, Live Life".
//                                     Together with our international headquarters in Toronto,
//                                     Canada, we provide insurance and financial advisory services,
//                                     operating under the Manulife brand in Canada,
//                                     Asia and Europe, and John Hancock in the United States.
//                                 </p>
//                                 <Link to="" className="align-self-start btn btn-secondary py-3 px-5"
//                                 >More Details</Link>
//                             </div>
//                         </div>
//                         <div className="col-lg-6 facts-counter wow fadeIn" data-wow-delay="0.5s">
//                             <div className="h-100 px-4 pe-lg-0">
//                                 <div className="row g-5">
//                                     <div className="col-sm-6">
//                                         <h1 className="display-5" id="counter-up" style={{fontWeight: "bold"}}>6368</h1>
//                                         <p className="fs-5 text-primary">Happy Clients</p>
//                                     </div>
//                                     <div className="col-sm-6">
//                                         <h1 className="display-5" data-toggle="counter-up" style={{fontWeight: "bold"}}>3636</h1>
//                                         <p className="fs-5 text-primary">Projects Succeed</p>
//                                     </div>
//                                     <div className="col-sm-6">
//                                         <h1 className="display-5" data-toggle="counter-up" style={{fontWeight: "bold"}}>1002</h1>
//                                         <p className="fs-5 text-primary">Awards Achieved</p>
//                                     </div>
//                                     <div className="col-sm-6">
//                                         <h1 className="display-5" data-toggle="counter-up" style={{fontWeight: "bold"}}>2010</h1>
//                                         <p className="fs-5 text-primary">Team Members</p>
//                                     </div>
//                                 </div>
//                             </div>
//                         </div>
//                     </div>
//                 </div>
//             </div>
//
//             <div className="container-xxl py-5">
//                 <div className="container">
//                     <div className="row g-5">
//                         <div className="col-lg-6 wow fadeInUp" data-wow-delay="0.1s">
//                             <h1 className="display-6 mb-5" style={{fontWeight: "bold"}}>Few Reasons Why People Choosing Us!</h1>
//                             <p className="mb-4">
//                                 One of FiveSuperHero's priorities in the recently introduced 'Spread Impact'
//                                 program is to open inclusive economic opportunities for all,
//                                 promoting the upward mobility of minorities.
//                                 and make financial solutions more accessible.
//                             </p>
//                             <div className="row g-3">
//                                 <div className="col-sm-6 wow fadeIn" data-wow-delay="0.1s">
//                                     <div className="bg-light rounded h-100 p-3">
//                                         <div
//                                             className="bg-white d-flex flex-column justify-content-center text-center rounded h-100 py-4 px-3"
//                                         >
//                                             <img
//                                                 className="align-self-center mb-3"
//                                                 src={img_icon_6_pri}
//                                                 alt=""
//                                             />
//                                             <h5 className="mb-0">Easy Process</h5>
//                                         </div>
//                                     </div>
//                                 </div>
//                                 <div className="col-sm-6 wow fadeIn" data-wow-delay="0.2s">
//                                     <div className="bg-light rounded h-100 p-3">
//                                         <div
//                                             className="bg-white d-flex flex-column justify-content-center text-center rounded py-4 px-3"
//                                         >
//                                             <img
//                                                 className="align-self-center mb-3"
//                                                 src={img_icon_3_pri}
//                                                 alt=""
//                                             />
//                                             <h5 className="mb-0">Fast Delivery</h5>
//                                         </div>
//                                     </div>
//                                 </div>
//                                 <div className="col-sm-6 wow fadeIn" data-wow-delay="0.3s">
//                                     <div className="bg-light rounded h-100 p-3">
//                                         <div
//                                             className="bg-white d-flex flex-column justify-content-center text-center rounded py-4 px-3"
//                                         >
//                                             <img
//                                                 className="align-self-center mb-3"
//                                                 src={img_icon_4_pri}
//                                                 alt=""
//                                             />
//                                             <h5 className="mb-0">Policy Controlling</h5>
//                                         </div>
//                                     </div>
//                                 </div>
//                                 <div className="col-sm-6 wow fadeIn" data-wow-delay="0.4s">
//                                     <div className="bg-light rounded h-100 p-3">
//                                         <div
//                                             className="bg-white d-flex flex-column justify-content-center text-center rounded h-100 py-4 px-3"
//                                         >
//                                             <img
//                                                 className="align-self-center mb-3"
//                                                 src={img_icon_7_pri}
//                                                 alt=""
//                                             />
//                                             <h5 className="mb-0">Money Saving</h5>
//                                         </div>
//                                     </div>
//                                 </div>
//                             </div>
//                         </div>
//                         <div className="col-lg-6 wow fadeInUp" data-wow-delay="0.5s">
//                             <div
//                                 className="position-relative rounded overflow-hidden h-100"
//                                 style={{minHeight: "400px"}}
//                             >
//                                 <img
//                                     className="position-absolute w-100 h-100"
//                                     src={img_feature}
//                                     alt=""
//                                     style={{objectFit: "cover"}}
//                                 />
//                             </div>
//                         </div>
//                     </div>
//                 </div>
//             </div>
//
//             <div className="container-xxl py-5 wow fadeIn">
//                 <div className="container">
//                     <div className="text-center mx-auto" style={{maxWidth: "500px"}}>
//                         <h1 className="display-6 mb-5" style={{fontWeight: "bold"}}>
//                             We Provide professional Insurance Services
//                         </h1>
//                     </div>
//                     <div className="row g-4 justify-content-center wow fadeIn" data-wow-delay="0.3s">
//                         {list}
//                     </div>
//                 </div>
//             </div>
//
//             <div
//                 className="container-fluid appointment my-5 py-5 wow fadeIn"
//                 data-wow-delay="0.1s"
//             >
//                 <div className="container py-5">
//                     <div className="row g-5">
//                         <div className="col-lg-6 wow fadeIn" data-wow-delay="0.3s">
//                             <h1 className="display-6 text-white mb-5" style={{fontWeight: "bold"}}>
//                                 We're Award Winning Insurance Company
//                             </h1>
//                             <p className="text-white mb-5">
//                                 The purpose of FiveSuperHero is to help people achieve the best in life.
//                                 FiveSuperHero provides a comprehensive healthcare solution at a reasonable cost,
//                                 helping to protect finances and increase assets,
//                                 and help people plan energy savings for life goals.
//                             </p>
//                             <div className="bg-white rounded p-3">
//                                 <div className="d-flex align-items-center bg-primary rounded p-3">
//                                     <img
//                                         className="flex-shrink-0 rounded-circle me-3"
//                                         src={img_profile}
//                                         alt=""
//                                     />
//                                     <h5 className="text-white mb-0">Call Us: +012 345 6789</h5>
//                                 </div>
//                             </div>
//                         </div>
//                         <div className="col-lg-6 wow fadeIn" data-wow-delay="0.5s">
//                             <div className="bg-white rounded p-5">
//                                 <Form onFinish={onFinish}>
//                                     <div className="row g-3">
//                                         <div className="col-sm-6">
//                                             <label htmlFor="gname">Your Name: </label>
//                                             <div className="">
//                                                 <input
//                                                     type="text"
//                                                     className="form-control"
//                                                     id="fullname"
//                                                     placeholder="Your Name"
//                                                     required=""
//                                                 />
//                                             </div>
//                                         </div>
//                                         <div className="col-sm-6">
//                                             <label htmlFor="gmail">Your Email: </label>
//                                             <div className="">
//                                                 <input
//                                                     type="email"
//                                                     className="form-control"
//                                                     id="gmail"
//                                                     placeholder="Your Email"
//                                                     required=""
//                                                 />
//                                             </div>
//                                         </div>
//                                         <div className="col-sm-6">
//                                             <label htmlFor="cname">Your Mobile: </label>
//                                             <div className="">
//                                                 <input
//                                                     type="text"
//                                                     className="form-control"
//                                                     id="phonenumber"
//                                                     placeholder="Your Name"
//                                                     style={{color: "#000"}}
//                                                     required=""
//                                                 />
//                                             </div>
//                                         </div>
//                                         <div className="col-sm-6">
//                                             <label htmlFor="cage">Service Type: </label>
//                                             <div className="">
//                                                 <select id="insurance" className="form-control">
//                                                     {insurance}
//                                                 </select>
//                                             </div>
//                                         </div>
//                                         <div className="col-12">
//                                             <label htmlFor="message">Message: </label>
//                                             <div className="">
//                       <textarea
//                           className="form-control"
//                           placeholder="Leave a message here"
//                           id="message"
//                           style={{height: "80px"}}
//                           required=""
//                       ></textarea>
//                                             </div>
//                                         </div>
//                                         <div className="col-12">
//                                             <button className="btn btn-primary py-3 px-5" type="submit">
//                                                 Get Appointment
//                                             </button>
//                                         </div>
//                                     </div>
//                                 </Form>
//                             </div>
//                         </div>
//                     </div>
//                 </div>
//             </div>
//
//             <Footer />
//         </div>
//     )
// }
//
// export default Home;
