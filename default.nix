# default.nix
let
  nixpkgs = fetchTarball "https://github.com/NixOS/nixpkgs/tarball/nixos-24.05";
  pkgs = import nixpkgs { config = {}; overlays = []; };
  build = pkgs.callPackage ./build.nix { };
in 
{
  inherit build;
  shell = pkgs.mkShellNoCC {
    inputsFrom = [ build ];
  };
}
