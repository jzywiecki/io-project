import React, { useEffect } from "react";
import './App.css'
import ClassSchedulerPage from './pages/ClassSchedulerPage'
import RoomListPage from './pages/RoomListPage'
import { BrowserRouter, Routes, Route } from "react-router-dom";
import SummaryRoomPage from './pages/SummaryRoomPage';
import VotingPage from './pages/VotingPage';
import AllUsersResults from './components/allUsersResults/allUsersResults';
import Results from './components/userResults/userResults';
import LoginAndRegisterPage from "./pages/LoginAndRegisterPage";
import ConfirmPage from "./pages/ConfirmPage";
import NoPermitionPage from "./pages/NoPermitionPage";
import LoginContext from "./contexts/Login.context";
import LogoutButton from "./components/LogoutButton";
import HomeButton from "./components/HomeButton";

function App () {
  return (
    <div className='App' >
      <LoginContext>
        <BrowserRouter>
          <LogoutButton className={"absolute top-0 right-2"}/>
          <HomeButton className={"aboslute top-0 left-2"}/>
          <Routes>
            <Route path="/" element={<RoomListPage />}/>
            <Route path="/room/:roomId" element={<SummaryRoomPage />}/>
            <Route path="/addRoom" element={<ClassSchedulerPage />}/>
            <Route path="/enroll/:roomId" element={<VotingPage />}/>
            <Route path="/results/:roomId" element={<AllUsersResults />}/>
            <Route path="/result/:roomId" element={<Results />}/>
            <Route path="/login" element={<LoginAndRegisterPage/>}/>
            <Route path="/confirmEmail/:token" element={<ConfirmPage/>}/>
            <Route path="/noPermition" element={<NoPermitionPage/>}/>
          </Routes>
        </BrowserRouter>
      </LoginContext>
    </div>
  )
}

export default App
