import React from 'react'
import { Route, Routes } from 'react-router-dom'
import Login from './Components/Account/Login/Login'
import Register from './Components/Account/Register/Register'
import Dashboard from './Components/AdminApp/Dashboard/Dashboard'
// import Home from './Components/Home/Home'

function Public() {
    return (
        <div>
            <Routes >
                {/*<Route path='/' element={<Home />} />*/}
                <Route path='/dashboard' element={<Dashboard />} />
                // Auth
                <Route path='/login' element={<Login />} />
                <Route path='/register' element={<Register />} />

            </Routes>
        </div>
    )
}

export default Public
