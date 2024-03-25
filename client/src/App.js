import React from "react";
import './App.css'
import ClassSchedulerPage from './pages/ClassSchedulerPage'
import RoomListPage from './pages/RoomListPage'
import { BrowserRouter, Routes, Route } from "react-router-dom";
import SummaryRoomPage from './pages/SummaryRoomPage';
import VotingPage from './pages/VotingPage';
import AllUsersResults from './components/allUsersResults/allUsersResults';
import Results from './components/userResults/userResults';

function App () {
  return (
    <div className='App' >
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<RoomListPage />}/>
          <Route path="/room/:roomId" element={<SummaryRoomPage />}/>
          <Route path="/addRoom" element={<ClassSchedulerPage />}/>
          <Route path="/enroll/:roomId/:userId" element={<VotingPage />}/>
          <Route path="/results/:roomId" element={<AllUsersResults />}/>
          <Route path="/result/:roomId/:userId" element={<Results />}/>
        </Routes>
      </BrowserRouter>
      {/* <ClassSchedulerPage/>
      <RoomListPage/> */}
    </div>
  )
}

export default App
