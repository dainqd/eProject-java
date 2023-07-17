import React, {useEffect, useState} from 'react'
import {Link, useNavigate} from 'react-router-dom'
import accountService from "../../../Service/AccountService";

function Sidebar() {
    const AuthName = sessionStorage.getItem("username");
    const navigate = useNavigate();

    const [data, setData] = useState([]);

    const isUser = async () => {
        await accountService.findUserByUsername(AuthName)
            .then((res) => {
                if (res.status === 200){
                    setData(res.data);
                    if (res.data.role === "ADMIN"){
                        localStorage.setItem('isAdmin', 1);
                    } else {
                        navigate('/profile')
                    }
                }
            })
    };

    useEffect(() => {
        isUser();
    }, []);

    return (
        <div>
            <aside id="sidebar" className="sidebar">
                <ul className="sidebar-nav" id="sidebar-nav">
                    <li className="nav-item">
                        <Link className="nav-link " to="/dashboard">
                            <i className="bi bi-grid" />
                            <span>Dashboard</span>
                        </Link>
                    </li>
                    <li className="nav-item">
                        <Link className="nav-link collapsed" data-bs-target="#forms-nav" data-bs-toggle="collapse" to="#">
                            <i className="bi bi-journal-text" /><span>Account</span><i className="bi bi-chevron-down ms-auto" />
                        </Link>
                        <ul id="forms-nav" className="nav-content collapse " data-bs-parent="#sidebar-nav">
                            <li>
                                <Link to="/account/list">
                                    <i className="bi bi-circle" /><span>List Account</span>
                                </Link>
                            </li>
                            <li>
                                <Link to="/account/create">
                                    <i className="bi bi-circle" /><span>Create Account</span>
                                </Link>
                            </li>
                        </ul>
                    </li>
                    <li className="nav-item">
                        <Link className="nav-link collapsed" data-bs-target="#tables-nav" data-bs-toggle="collapse" to="#">
                            <i className="bi bi-layout-text-window-reverse" /><span>Insurance</span><i className="bi bi-chevron-down ms-auto" />
                        </Link>
                        <ul id="tables-nav" className="nav-content collapse " data-bs-parent="#sidebar-nav">
                            <li>
                                <Link to="/insurance/list">
                                    <i className="bi bi-circle" /><span>List Insurance</span>
                                </Link>
                            </li>
                            <li>
                                <Link to="/insurance/create">
                                    <i className="bi bi-circle" /><span>Create Insurance</span>
                                </Link>
                            </li>
                            <li>
                                <Link to="/insurance/manage">
                                    <i className="bi bi-circle" /><span>Manage Insurance</span>
                                </Link>
                            </li>
                        </ul>
                    </li>
                    <li className="nav-item">
                        <Link className="nav-link collapsed" data-bs-target="#charts-nav" data-bs-toggle="collapse" to="#">
                            <i className="bi bi-bar-chart" /><span>Customer</span><i className="bi bi-chevron-down ms-auto" />
                        </Link>
                        <ul id="charts-nav" className="nav-content collapse " data-bs-parent="#sidebar-nav">
                            <li>
                                <Link to="/customer/list">
                                    <i className="bi bi-circle" /><span>List Customer</span>
                                </Link>
                            </li>
                            <li>
                                <Link to="/customer/create">
                                    <i className="bi bi-circle" /><span>Create Customer</span>
                                </Link>
                            </li>
                            <li>
                                <Link to="/customer/manage">
                                    <i className="bi bi-circle" /><span>Manage Customer</span>
                                </Link>
                            </li>
                        </ul>
                    </li>

                    <li className="nav-item">
                        <Link className="nav-link collapsed" to="/appointment/list">
                            <i className="bi bi-card-list"></i>
                            <span>Appointment</span>
                        </Link>
                    </li>

                    <li className="nav-item">
                        <Link className="nav-link collapsed" data-bs-target="#components-nav" data-bs-toggle="collapse"
                           to="#">
                            <i className="bi bi-menu-button-wide"></i><span>Member</span><i
                            className="bi bi-chevron-down ms-auto"></i>
                        </Link>
                        <ul id="components-nav" className="nav-content collapse " data-bs-parent="#sidebar-nav">
                            <li>
                                <Link to="/member/list">
                                    <i className="bi bi-circle"></i><span>List Member</span>
                                </Link>
                            </li>
                            <li>
                                <Link to="/member/create">
                                    <i className="bi bi-circle"></i><span>Create Member</span>
                                </Link>
                            </li>
                        </ul>
                    </li>

                    <li className="nav-item">
                        <Link className="nav-link collapsed" to="/order/list">
                            <i className="bi bi-cart-dash"></i>
                            <span>Order</span>
                        </Link>
                    </li>
                    
                    <li className="nav-item">
                        <Link className="nav-link collapsed" to="/payment/list">
                            <i className="bi bi-person"></i>
                            <span>Payment</span>
                        </Link>
                    </li>

                    <li className="nav-item">
                        <Link className="nav-link collapsed" to="/transaction/list">
                            <i className="bi bi-file-earmark"></i>
                            <span>Transaction</span>
                        </Link>
                    </li>

                    <li className="nav-item">
                        <Link className="nav-link collapsed" to="/feedback/list">
                            <i className="bi bi-question-circle"></i>
                            <span>Feedback</span>
                        </Link>
                    </li>

                    <li className="nav-item">
                        <Link className="nav-link collapsed" data-bs-target="#icons-nav" data-bs-toggle="collapse" to="#">
                            <i className="bi bi-gem" /><span>Permission</span><i className="bi bi-chevron-down ms-auto" />
                        </Link>
                        <ul id="icons-nav" className="nav-content collapse " data-bs-parent="#sidebar-nav">
                            <li>
                                <Link to="#">
                                    <i className="bi bi-circle" /><span>Upgrade Role</span>
                                </Link>
                            </li>
                            <li>
                                <Link to="#">
                                    <i className="bi bi-circle" /><span>Upgrade Status</span>
                                </Link>
                            </li>
                        </ul>
                    </li>
                </ul>
            </aside>{/* End Sidebar*/}
        </div>

    )
}

export default Sidebar
