'use client';

import Logo from "@/assets/logosaas.png";
import Image from "next/image";
import MenuIcon from "@/assets/menu.svg";
import Link from "next/link";

const Navbar = () => {
    return(
        <header className="sticky top-0 backdrop-blur-sm z-20">
        <div className='py-5 px-20'>
          <div className='flex items-center justify-between'>
            <Image src={Logo} alt="Logo" height={40} width={40} />
            <span className="text-2xl font-bold text-transparent bg-gradient-to-r from-gray-900 to-violet-800 bg-clip-text absolute left-[130px]">BlogApp</span>
            <MenuIcon className="h-5 w-5 md:hidden" />
            <nav className='hidden md:flex gap-6 text-white items-center'>
              <a href='/' className="hover:text-slate-300 hover:scale-110 transition-all ease-in-out">Explore</a>
              <a href='/about' className="hover:text-slate-300 hover:scale-110 transition-all ease-in-out">About</a>
              <a href='/feed' className="hover:text-slate-300 hover:scale-110 transition-all ease-in-out">Home</a>
              <a href='/help' className="hover:text-slate-300 hover:scale-110 transition-all ease-in-out">Help</a>
              <Link href="/signup" className='bg-white text-black px-4 py-2 rounded-lg font-medium inline-flex items-center justify-center tracking-tight'>
                Sign up
              </Link>
            </nav>
          </div>
        </div>
        </header>
    )
}

export default Navbar;