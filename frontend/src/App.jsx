import Sidebar from "./components/layout/Sidebar";
import Navbar from "./components/layout/Navbar";
import AppRoutes from "./routes/AppRoutes";

export default function App() {
  return (
    <div className="app">
      <Sidebar />
      <div className="main">
        <Navbar />
        <main className="content">
          <AppRoutes />
        </main>
      </div>
    </div>
  );
}
