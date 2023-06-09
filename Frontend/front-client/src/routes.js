import React from 'react';
import { BrowserRouter, Route, Routes, useLocation } from 'react-router-dom';
import Login from './pages/Login';
import Users from './pages/Users';
import NewUser from './pages/NewUser';
import NewAdress from './pages/NewAdress';
import Page404 from './pages/Page404';
import NavBar from './components/NavBar';

function AppRoutes() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<WithNavBar><Login /></WithNavBar>} />
                <Route path="/users" element={<WithNavBar><Users /></WithNavBar>} />
                <Route path="/user/new/:userId" element={<WithNavBar><NewUser /></WithNavBar>} />
                <Route path="/user/newadress/:userId/:adressId" element={<WithNavBar><NewAdress /></WithNavBar>} />
                <Route path="*" element={<Page404 />} />
            </Routes>
        </BrowserRouter>
    );
}

function WithNavBar({ children }) {
    const location = useLocation();

    const isLoginPage = location.pathname === '/';

    return (
        <>
            {!isLoginPage && <NavBar />}

            {children}
        </>
    );
}

export default AppRoutes;
