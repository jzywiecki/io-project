import './App.css'
import ClassSchedulerPage from './pages/ClassSchedulerPage'
import RoomListPage from './pages/RoomListPage'
import { BrowserRouter, Routes, Route } from "react-router-dom";
import SummaryRoomPage from './pages/SummaryRoomPage';
function App () {
  return (
    <div className='App' >
      <BrowserRouter>
        <Routes>
          <Route path="/addRoom" element={<ClassSchedulerPage />}/>
          <Route path="/roomList" element={<RoomListPage />}/>
          <Route path="/room/:roomId" element={<SummaryRoomPage />}/>
        </Routes>
      </BrowserRouter>
      {/* <ClassSchedulerPage/>
      <RoomListPage/> */}
    </div>
  )
}

export default App
